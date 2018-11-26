package br.gov.ce.seas.sgp.cadastro.util;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class UpperCaseDocument extends PlainDocument
{
	
	private static final long serialVersionUID = -2965065559484828588L;
	
	public void insertString(int offs, String str, AttributeSet a) throws BadLocationException
	{
		
		if (str == null)
			return;
		
		char[] upper = str.toCharArray();
		for (int i = 0; i < upper.length; i++)
			upper[i] = Character.toUpperCase(upper[i]);
		
		super.insertString(offs, new String(upper), a);
	}
}
