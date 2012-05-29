package com.novamente.nlgen.unary;


import bsh.EvalError;
import bsh.Interpreter;


import com.novamente.nlgen.util.WordNode;

public class ScriptAlgorithm extends AbstractAlgorithm {
	String script;

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}

	@Override
	public WordNode apply(WordNode node, WordNode preNode) {
		Interpreter i = new Interpreter();
		try {
			i.set("wordNode", node);
			i.eval(getScript());
			node = (WordNode) i.get("wordNode");
		} catch (EvalError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return node;
	}
}
