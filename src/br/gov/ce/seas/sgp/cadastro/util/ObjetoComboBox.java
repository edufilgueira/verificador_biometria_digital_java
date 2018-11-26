package br.gov.ce.seas.sgp.cadastro.util;

public class ObjetoComboBox
{
	
	private String codigo;
	
	private String descricao;
	
	public ObjetoComboBox(String codigo, String descricao)
	{
		this.codigo = codigo;
		this.descricao = descricao;
	}
	
	@Override
	public String toString()
	{
		return this.descricao;
	}
	
	public String getCodigo()
	{
		return this.codigo;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (obj == null)
			return false;
		
		return ((ObjetoComboBox) obj).getCodigo().equals(codigo);
	}
	
}