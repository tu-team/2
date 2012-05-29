package fi.neter.opencog.reasoning.pln;

import java.util.List;

import fi.neter.opencog.reasoning.pln.rules.Rule;

/**
 * From file PLNEvaluator.h
 * class DefaultRuleProvider : public RuleProvider
 * @author tero
 *
 */
public class DefaultRuleProvider {

	/**
	 * std::vector<RULE> rs;
	 */
	protected native void setRs(List<Rule> rs);
	
	/**
	 * DefaultRuleProvider();
	 */
	public native DefaultRuleProvider DefaultRuleProvider();
	
	/**
	 * virtual ~DefaultRuleProvider();
	 */
	public native void destroy();
	
	/**
	 * const std::vector<RULE>& get();
	 */
	public native List<Rule> get();
}
