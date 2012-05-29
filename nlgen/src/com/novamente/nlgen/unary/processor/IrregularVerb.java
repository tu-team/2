package com.novamente.nlgen.unary.processor;

import java.util.HashMap;
import java.util.Map;

public class IrregularVerb {
	public static final int PAST_TENSE = 0;

	public static final int PAST_PARTICIPLE = 1;

	private static Map<String, String[]> irregular = new HashMap<String, String[]>();
	static {
		irregular.put("", new String[] { "", "" });
		irregular.put("beat", new String[] { "beat", "beaten" });
		irregular.put("become", new String[] { "became", "become" });
		irregular.put("begin", new String[] { "began", "begun" });
		irregular.put("blow", new String[] { "blew", "blown" });
		irregular.put("break", new String[] { "broke", "broken" });
		irregular.put("bring", new String[] { "brought", "brought" });
		irregular.put("build", new String[] { "built", "built" });
		irregular.put("burst", new String[] { "burst", "burst" });
		irregular.put("buy", new String[] { "bought", "bought" });
		irregular.put("catch", new String[] { "caught", "caught" });
		irregular.put("choose", new String[] { "chose", "chosen" });
		irregular.put("come", new String[] { "came", "come" });
		irregular.put("cost", new String[] { "cost", "cost" });
		irregular.put("cut", new String[] { "cut", "cut" });
		irregular.put("dig", new String[] { "dug", "dug" });
		irregular.put("do", new String[] { "did", "done" });
		irregular.put("draw", new String[] { "drew", "drawn" });
		irregular.put("eat", new String[] { "ate", "eaten" });
		irregular.put("fall", new String[] { "fell", "fallen" });
		irregular.put("fell", new String[] { "felt", "felt" });
		irregular.put("fight", new String[] { "fought", "fought" });
		irregular.put("find", new String[] { "found", "found" });
		irregular.put("fly", new String[] { "flew", "flown" });
		irregular.put("freeze", new String[] { "froze", "frozen" });
		irregular.put("get", new String[] { "got", "got" });
		irregular.put("give", new String[] { "gave", "given" });
		irregular.put("go", new String[] { "went", "gone" });
		irregular.put("grow", new String[] { "grew", "grown" });
		irregular.put("have", new String[] { "had", "had" });
		irregular.put("hear", new String[] { "heard", "heard" });
		irregular.put("hide", new String[] { "hid", "hidden" });
		irregular.put("hurt", new String[] { "hurt", "hurt" });
		irregular.put("keep", new String[] { "kept", "kept" });
		irregular.put("know", new String[] { "knew", "known" });
		irregular.put("lay", new String[] { "laid", "laid" });
		irregular.put("lead", new String[] { "led", "led" });
		irregular.put("leave", new String[] { "left", "left" });
		irregular.put("lend", new String[] { "lent", "lent" });
		irregular.put("let", new String[] { "let", "let" });
		irregular.put("lie", new String[] { "lay", "lain" });
		irregular.put("lose", new String[] { "lost", "lost" });
		irregular.put("make", new String[] { "made", "made" });
		irregular.put("mean", new String[] { "meant", "meant" });
		irregular.put("meet", new String[] { "met", "met" });
		irregular.put("pay", new String[] { "paid", "paid" });
		irregular.put("put", new String[] { "put", "put" });
		irregular.put("read", new String[] { "read", "read" });
		irregular.put("ride", new String[] { "rode", "ridden" });
		irregular.put("ring", new String[] { "rang", "rung" });
		irregular.put("rise", new String[] { "rose", "risen" });
		irregular.put("run", new String[] { "ran", "run" });
		irregular.put("say", new String[] { "said", "said" });
		irregular.put("see", new String[] { "saw", "seen" });
		irregular.put("sell", new String[] { "sold", "sold" });
		irregular.put("send", new String[] { "sent", "sent" });
		irregular.put("set", new String[] { "set", "set" });
		irregular.put("shake", new String[] { "shook", "shaken" });
		irregular.put("shine", new String[] { "shone", "shone" });
		irregular.put("shoot", new String[] { "shot", "shot" });
		irregular.put("show", new String[] { "showed", "shown" });
		irregular.put("shut", new String[] { "shut", "shut" });
		irregular.put("sing", new String[] { "sang", "sung" });
		irregular.put("sink", new String[] { "sank", "sunk" });
		irregular.put("sit", new String[] { "sat", "sat" });
		irregular.put("sleep", new String[] { "slept", "slept" });
		irregular.put("speak", new String[] { "spoke", "spoken" });
		irregular.put("spend", new String[] { "spent", "spent" });
		irregular.put("spin", new String[] { "span", "spun" });
		irregular.put("spread", new String[] { "spread", "spread" });
		irregular.put("spring", new String[] { "sprang", "sprung" });
		irregular.put("stand", new String[] { "stood", "stood" });
		irregular.put("steal", new String[] { "stole", "stolen" });
		irregular.put("stick", new String[] { "stuck", "stuck" });
		irregular.put("strike", new String[] { "struck", "struck" });
		irregular.put("sweep", new String[] { "swept", "swept" });
		irregular.put("swim", new String[] { "swam", "swum" });
		irregular.put("take", new String[] { "took", "taken" });
		irregular.put("teach", new String[] { "taught", "taught" });
		irregular.put("tear", new String[] { "tore", "torn" });
		irregular.put("tell", new String[] { "told", "told" });
		irregular.put("think", new String[] { "thought", "thought" });
		irregular.put("throw", new String[] { "threw", "thrown" });
		irregular
				.put("understand", new String[] { "understood", "understood" });
		irregular.put("wake", new String[] { "woke", "woken" });
		irregular.put("wear", new String[] { "wore", "worn" });
		irregular.put("weep", new String[] { "wept", "wept" });
		irregular.put("win", new String[] { "won", "won" });
		irregular.put("write", new String[] { "wrote", "written" });
		irregular.put("is", new String[] { "was", "was" });
		irregular.put("are", new String[] { "were", "were" });
	}

	public static boolean isIrregular(String word) {
		word = word.trim();
		if (word.matches(".*_\\d+")) {
			word = word.substring(0, word.lastIndexOf("_"));
		}
		return irregular.containsKey(word);
	}

	public static String getPast(String word) {
		return getPast(word,PAST_TENSE);
	}

	public static String getPast(String word, int type) {
		word = word.trim();
		if (word.matches(".*_\\d+")) {
			word = word.substring(0, word.lastIndexOf("_"));
		}
		if(irregular.containsKey(word)){
		String result = irregular.get(word)[type];
		return result;
		}else{
			return null;
		}
	}
}
