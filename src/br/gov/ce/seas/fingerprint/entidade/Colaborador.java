/**
 * 
 */
package br.gov.ce.seas.fingerprint.entidade;

/**
 * @author Bisso
 *
 */
public class Colaborador {
	
	private Integer id;
	private String nome;
        private String cpf;
        private Integer area_id;
        private String role;
        private String created_at;
        private String updated_at;
        private Integer grade_id;
        private String password_digest;
        private String email;
        private boolean ativo;
        private String data_desvinculacao;
        private Integer subarea_id;
	private byte[] digitalEsquerda;
        private byte[] digitalDireita;
        private Boolean hasDigital;
	
	public Colaborador() {

	}
	
	public Colaborador(Integer id, String nome, String cpf, Integer area_id, String role, 
            String created_at, String updated_at, Integer grade_id, String password_digest,
            String email, boolean ativo, String data_desvinculacao, Integer subarea_id) {
            this.id = id;
            this.nome = nome;
            this.cpf = cpf;
            this.area_id = area_id;
            this.role = role;
            this.created_at = created_at;
            this.updated_at = updated_at;
            this.grade_id = grade_id;
            this.password_digest = password_digest;
            this.email = email;
            this.ativo = ativo;
            this.data_desvinculacao = data_desvinculacao;
            this.subarea_id = subarea_id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

        public String getCpf() {
            return cpf;
        }

        public void setCpf(String cpf) {
            this.cpf = cpf;
        }

        public Integer getArea_id() {
            return area_id;
        }

        public void setArea_id(Integer area_id) {
            this.area_id = area_id;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public Integer getGrade_id() {
            return grade_id;
        }

        public void setGrade_id(Integer grade_id) {
            this.grade_id = grade_id;
        }

        public String getPassword_digest() {
            return password_digest;
        }

        public void setPassword_digest(String password_digest) {
            this.password_digest = password_digest;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public boolean isAtivo() {
            return ativo;
        }

        public void setAtivo(boolean ativo) {
            this.ativo = ativo;
        }

        public String getData_desvinculacao() {
            return data_desvinculacao;
        }

        public void setData_desvinculacao(String data_desvinculacao) {
            this.data_desvinculacao = data_desvinculacao;
        }

        public Integer getSubarea_id() {
            return subarea_id;
        }

        public void setSubarea_id(Integer subarea_id) {
            this.subarea_id = subarea_id;
        }

	public byte[] getDigitalEsquerda() {
		return digitalEsquerda;
	}

	public void setDigitalEsquerda(byte[] digitalEsquerda) {
		this.digitalEsquerda = digitalEsquerda;
	}
	
	public byte[] getDigitalDireita() {
		return digitalDireita;
	}

	public void setDigitalDireita(byte[] digitalDireita) {
		this.digitalDireita = digitalDireita;
	}
	
	public Boolean getHasDigital()
	{
            if (getDigitalDireita() != null && getDigitalEsquerda() != null)
		hasDigital = true;
            else
		hasDigital = false;
            return hasDigital;
	}

}