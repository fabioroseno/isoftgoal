package br.com.isoftgoal.dominio;

import java.io.Serializable;
import java.util.*;

import javax.persistence.*;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;

import br.com.isoftgoal.enums.GrupoUsuario;
import br.com.isoftgoal.util.I18nUtil;
import entities.Context;
import entities.Repository;
import entities.annotations.*;
import entities.descriptor.PropertyType;

@Entity
@Table(name = "usuarios")
@NamedQueries({
	@NamedQuery(name = "Autenticacao", query = " From Usuario u Where u.nomeDeUsuario = :nomeDeUsuario And u.senha = :senha "),
    @NamedQuery(name = "Administratores", query = " From Usuario u Where 'Admin' in elements(u.gruposUsuarios) "),
	@NamedQuery(name = "ValidaUserNameEmail", query = " From Usuario u Where u.nomeDeUsuario = :nomeDeUsuario Or u.email = :email ")})
@Views({
    @View(name = "autenticacao",
            title = "br.com.isoftgoal.dominio.Usuario.view.autenticacao.title",
            members = "[#nomeDeUsuario; #senha; entrar()]",
            namedQuery = "Select new br.com.isoftgoal.dominio.Usuario()",
            roles = "NOLOGGED"),
    @View(name = "cadastro",
            title = "br.com.isoftgoal.dominio.Usuario.view.cadastro.title",
            members = "[#nomeCompleto; #email; #nomeDeUsuario; #senha; #confirmeSenha; [cadastrar()]]", 
            namedQuery = "Select new br.com.isoftgoal.dominio.Usuario()",
            roles = "NOLOGGED"),
    @View(name = "perfil",
            title = "br.com.isoftgoal.dominio.Usuario.view.perfil.title",
            members = "['':*foto, [*nomeCompleto; *email; *nomeDeUsuario; *gruposUsuarios; [editarPerfil()]]]",
            namedQuery = " From Usuario u Where u = :nomeDeUsuario",
            params = {@Param(name = "nomeDeUsuario", value = "#{context.currentUser}")},
            roles = "LOGGED"),
    @View(name = "editarPerfil",
            title = "br.com.isoftgoal.dominio.Usuario.view.editarPerfil.title",
            hidden = true,
            members = "['':*foto, [#nomeCompleto, alterarFoto(); #email, alterarSenha(); [*nomeDeUsuario; *gruposUsuarios; [salvarPerfil()]]]]",
            namedQuery = " From Usuario u Where u = :nomeDeUsuario",
            params = {@Param(name = "nomeDeUsuario", value = "#{context.currentUser}")},
            roles = "LOGGED"),
    @View(name = "usuarios",
          title = "br.com.isoftgoal.dominio.Usuario.view.usuarios.title",
          members = "Users[nomeCompleto; nomeDeUsuario; senha; email; gruposUsuarios]:2, *foto",
          template = "@CRUD+@PAGER",
          roles = "Admin"),
    @View(name = "saida",
          title = "br.com.isoftgoal.dominio.Usuario.view.saida.title",
          members = "['':*foto, [Logout[msgSair; [sair(), cancelar()]]]]",
          namedQuery = "From Usuario u Where u = :nomeDeUsuario",
          params = {@Param(name = "nomeDeUsuario", value = "#{context.currentUser}")},
          roles = "LOGGED")
})
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final String MSG_CADASTRO_EFETUADO_I18N = "msg.cadastro.efetuado";
	private static final String MSG_EDICAO_EFETUADA_I18N = "msg.edicao.efetuada";
	private static final String NOVA_SENHA_I18N = "br.com.isoftgoal.dominio.Usuario.alterarSenha.arg[0].displayName";
	private static final String CONFIRMA_SENHA_I18N = "br.com.isoftgoal.dominio.Usuario.alterarSenha.arg[1].displayName";
	private static final String NOVA_FOTO_I18N = "br.com.isoftgoal.dominio.Usuario.alterarFoto.arg[0].displayName";
	
	private static final String MSG_NOME_USUARIO_INDISPONIVEL = I18nUtil.get("msg.nome.usuario.indisponivel");
	private static final String MSG_EMAIL_CADASTRADO = I18nUtil.get("msg.email.cadastrado");
	private static final String MSG_NOME_USUARIO_REQUERIDO = I18nUtil.get("msg.nome.usuario.requerido");
	private static final String MSG_NOME_COMPLETO_REQUERIDO = I18nUtil.get("msg.nome.completo.requerido");
	private static final String MSG_EMAIL_REQUERIDO = I18nUtil.get("msg.email.requerido");
	private static final String MSG_SENHA_REQUERIDA = I18nUtil.get("msg.senha.requerida");
	private static final String MSG_CONFIRMACAO_SENHA_REQUERIDA = I18nUtil.get("msg.confirmacao.requerida");
	private static final String MSG_NOME_SENHA_INVALIDOS = I18nUtil.get("msg.nome.senha.invalidos");
	private static final String MSG_SENHA_ALTERADA = I18nUtil.get("msg.senha.alterada");
	private static final String MSG_CONFIRMACAO_DIFERENTE = I18nUtil.get("msg.confirmacao.diferente");
	
	private static final String GO_PAGINA_INICIAL = "go:home";
	private static final String GO_LOGIN = "go:br.com.isoftgoal.dominio.Usuario@autenticacao";
	private static final String GO_PERFIL = "go:br.com.isoftgoal.dominio.Usuario@perfil";
	private static final String GO_EDITAR_PERFIL = "go:br.com.isoftgoal.dominio.Usuario@editarPerfil";
	private static final String GO_USUARIOS = "go:br.com.isoftgoal.dominio.Usuario@usuarios";
	
	private static final String ID_SQL_ADMINISTRADORES = "Administratores";
	private static final String ID_SQL_AUTENTICACAO = "Autenticacao";
	private static final String ID_SQL_VALIDA_NOME_USUARIO_EMAIL = "ValidaUserNameEmail";

    @Id
    @GeneratedValue
    @Column(name="cod_usuario")
    private Integer codUsuario;

    @NotEmpty
    @Column(name="nome_completo", length = 60)
    @PropertyDescriptor(displayWidth = 40)
    private String nomeCompleto;

    @NotEmpty
    @Column(length = 30)
    private String email;
    
    @NotEmpty
    @Username
    @Column(name="nome_de_usuario", length = 30, unique = true)
    private String nomeDeUsuario;

    @NotEmpty
    @Column(length = 32)
    @Type(type = "entities.security.Password")
    @PropertyDescriptor(secret = true, displayWidth = 30)
    private String senha;

    @Transient
    @PropertyDescriptor(secret = true, displayWidth = 30)
    private String confirmeSenha;

    @UserRoles
    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name="grupos_usuarios",
            joinColumns=@JoinColumn(name="cod_usuario")
      )
    @Column(name="grupo_usuario")
    private List<GrupoUsuario> gruposUsuarios = new ArrayList<GrupoUsuario>();

    @Lob
    @Editor(propertyType = PropertyType.IMAGE)
    private byte[] foto;

    @Transient
    @PropertyDescriptor(readOnly = true, hidden = true)
    private String msgSair;
    
    public Usuario() {
    }

    public Usuario(String nomeDeUsuario, String senha, GrupoUsuario... gruposUsuarios) {
        this.nomeDeUsuario = nomeDeUsuario;
        this.senha = senha;
        this.gruposUsuarios.addAll(Arrays.asList(gruposUsuarios));
    }
    
    @ActionDescriptor(preValidate = false, confirmMessage = MSG_CADASTRO_EFETUADO_I18N)
    public String cadastrar() {
    	
    	if("".equals(nomeCompleto.trim())) {
    		throw new SecurityException(MSG_NOME_COMPLETO_REQUERIDO);
    	}
    	
    	if("".equals(email.trim())) {
    		throw new SecurityException(MSG_EMAIL_REQUERIDO);
    	}
    	
    	if("".equals(nomeDeUsuario.trim())) {
    		throw new SecurityException(MSG_NOME_USUARIO_REQUERIDO);
    	}
    	
    	if("".equals(senha.trim())) {
    		throw new SecurityException(MSG_SENHA_REQUERIDA);
    	}
    	
    	if("".equals(confirmeSenha.trim())) {
    		throw new SecurityException(MSG_CONFIRMACAO_SENHA_REQUERIDA);
    	}
    	
    	List<Usuario> usuarios = Repository.query(ID_SQL_VALIDA_NOME_USUARIO_EMAIL, nomeDeUsuario, email);
        if (usuarios.size() > 0) {
        	
        	for (Usuario usuario : usuarios) {
        		if(email.equals(usuario.getEmail())) {
            		throw new SecurityException(MSG_EMAIL_CADASTRADO);
            	} else if(nomeDeUsuario.equals(usuario.getNomeDeUsuario())) {
            		throw new SecurityException(MSG_NOME_USUARIO_INDISPONIVEL);
            	}
			}
        	
        }
        
        if(!senha.equals(confirmeSenha)) {
    		throw new SecurityException(MSG_CONFIRMACAO_DIFERENTE);
    	}
        
        Repository.save(this);
    	return GO_LOGIN;
    }

    @ActionDescriptor(preValidate = false)
    public String editarPerfil() {
    	return GO_EDITAR_PERFIL;
	}

    @ActionDescriptor(preValidate = false)
    public String entrar() {
    	if("".equals(nomeDeUsuario.trim())) {
    		throw new SecurityException(MSG_NOME_USUARIO_REQUERIDO);
    	}
    	
    	if("".equals(senha.trim())) {
    		throw new SecurityException(MSG_SENHA_REQUERIDA);
    	}

        if (Repository.queryCount(ID_SQL_ADMINISTRADORES) == 0) {
            Usuario admin = new Usuario(nomeDeUsuario, senha, GrupoUsuario.Admin);
            Repository.save(admin);
            Context.setCurrentUser(admin);
            return GO_USUARIOS;
        } else {
            List<Usuario> users = Repository.query(ID_SQL_AUTENTICACAO, nomeDeUsuario, senha);
            if (users.size() == 1) {
                Context.setCurrentUser((Usuario) users.get(0));
            } else {
                throw new SecurityException(MSG_NOME_SENHA_INVALIDOS);
            }
        }
        return GO_PAGINA_INICIAL;
    }

    @ActionDescriptor(preValidate = false)
    static public String sair() {
        Context.clear();
        return GO_LOGIN;
    }

    @ActionDescriptor(preValidate = false)
    public String cancelar() {
        return GO_PAGINA_INICIAL;
    }

    @ActionDescriptor(confirmMessage = MSG_EDICAO_EFETUADA_I18N, preValidate = false)
    public String salvarPerfil() {
    	if("".equals(nomeCompleto.trim())) {
    		throw new SecurityException(MSG_NOME_COMPLETO_REQUERIDO);
    	}
    	
    	if("".equals(email.trim())) {
    		throw new SecurityException(MSG_EMAIL_REQUERIDO);
    	}
    	
    	if (codUsuario != null) {
        	List<Usuario> usuarios = Repository.query(ID_SQL_VALIDA_NOME_USUARIO_EMAIL, nomeDeUsuario, email);
            if (usuarios.size() == 1) {
            	Repository.save(this);
            } else {
            	throw new SecurityException(MSG_EMAIL_CADASTRADO);
            }
        }
        return GO_PERFIL;
    }
    
	public String alterarSenha(
			@ParameterDescriptor(displayName = NOVA_SENHA_I18N, secret = true, required = true) String novaSenha,
			@ParameterDescriptor(displayName = CONFIRMA_SENHA_I18N, secret = true, required = true) String confSenha) {
		if (novaSenha.equals(confSenha)) {
			this.setSenha(novaSenha);
			Repository.save(this);
			return MSG_SENHA_ALTERADA;
		} else {
			throw new SecurityException(MSG_CONFIRMACAO_DIFERENTE);
		}
	}

    public void alterarFoto(@ParameterDescriptor(displayName = NOVA_FOTO_I18N) byte[] novaFoto) {
        this.foto = novaFoto;
        if (codUsuario != null) {
            Repository.save(this);
        }
    }

	public Integer getCodUsuario() {
		return codUsuario;
	}

	public void setCodUsuario(Integer codUsuario) {
		this.codUsuario = codUsuario;
	}

	public String getNomeCompleto() {
		return nomeCompleto;
	}

	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNomeDeUsuario() {
		return nomeDeUsuario;
	}

	public void setNomeDeUsuario(String nomeDeUsuario) {
		this.nomeDeUsuario = nomeDeUsuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getConfirmeSenha() {
		return confirmeSenha;
	}

	public void setConfirmeSenha(String confirmeSenha) {
		this.confirmeSenha = confirmeSenha;
	}

	public List<GrupoUsuario> getGruposUsuarios() {
		return gruposUsuarios;
	}

	public void setGruposUsuarios(List<GrupoUsuario> gruposUsuarios) {
		this.gruposUsuarios = gruposUsuarios;
	}

	public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}
	
	public String getMsgSair() {
		return msgSair;
	}

	public void setMsgSair(String msgSair) {
		this.msgSair = msgSair;
	}

	@Override
    public String toString() {
        return nomeDeUsuario;
    }
	
}
