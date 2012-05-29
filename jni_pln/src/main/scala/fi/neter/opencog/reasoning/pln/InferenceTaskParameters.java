package fi.neter.opencog.reasoning.pln;

/**
 * From file PLNEvaluator.h
 * @author tero
 *
 */
public class InferenceTaskParameters {
	/**
	 * InferenceTaskParameters(
	 * 				RuleProvider* _ruleProvider,
	 * 				Btr<tree<Vertex> > _target);
	 */
		
	/**
	 * RuleProvider* ruleProvider;
	 */
	public native void setRuleProvider(RuleProvider ruleProvider);
	public native RuleProvider getRuleProvider();
	
	/**
	 * Btr<vtree> target;
	 */
	public native void setTarget(VTree target);
	public native VTree getTarget();
}
