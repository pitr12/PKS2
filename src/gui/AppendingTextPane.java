package gui;

import java.awt.Color;
import java.awt.Rectangle;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class AppendingTextPane extends JTextPane {
	  public AppendingTextPane() {
	    super();
	  }

	  public AppendingTextPane(StyledDocument doc) {
	    super(doc);
	  }

	  // Appends text to the document and ensure that it is visible
	  public void appendText(String text, String source) {
		  StyledDocument doc = UI.textPane.getStyledDocument();
		  Color color  = Color.black;
		  
		  if("c".equals(source)){
			  color  = Color.blue;
		  }
		  if("s".equals(source)){
			  color  = Color.red;
		  }
		  
		  Style style = UI.textPane.addStyle("I'm a Style", null);
	        StyleConstants.setForeground(style, color);
	        
			try {
				doc.insertString( doc.getEndPosition().getOffset(), text, style );
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
			int len = doc.getLength();
			UI.textPane.setCaretPosition(len);
	  }
}
