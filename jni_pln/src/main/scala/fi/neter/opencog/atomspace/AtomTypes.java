package fi.neter.opencog.atomspace;

/**
 * From file: atom_types.h
 * @author tero
 *
 */
public enum AtomTypes {
	/**
	 * static const opencog::Type NOTYPE=((1 << (8 * sizeof(Type))) - 1);
	 */
	NOTYPE(65535),
	/*
	 * FIXME: No idea what numbers these others should map to.
	 */
	/**
	 * extern opencog::Type ATOM;
	 */
	ATOM(0),
	/**
	 * extern opencog::Type NODE;
	 */
	NODE(1),
	/**
	 * extern opencog::Type LINK;
	 */
	LINK(2),
	/**
	 * extern opencog::Type CONCEPT_NODE;
	 */
	CONCEPT_NODE(3),
	/**
	 * extern opencog::Type NUMBER_NODE;
	 */
	NUMBER_NODE(4),
	/**
	 * extern opencog::Type ORDERED_LINK;
	 */
	ORDERED_LINK(5),
	/**
	 * extern opencog::Type UNORDERED_LINK;
	 */
	UNORDERED_LINK(6),
	/**
	 * extern opencog::Type SET_LINK;
	 */
	SET_LINK(7),
	/**
	 * extern opencog::Type LIST_LINK;
	 */
	LIST_LINK(8),
	/**
	 * extern opencog::Type MEMBER_LINK;
	 */
	MEMBER_LINK(9),
	/**
	 * extern opencog::Type SUBSET_LINK;
	 */
	SUBSET_LINK(10),
	/**
	 * extern opencog::Type AND_LINK;
	 */
	AND_LINK(11),
	/**
	 * extern opencog::Type OR_LINK;
	 */
	OR_LINK(12),
	/**
	 * extern opencog::Type NOT_LINK;
	 */
	NOT_LINK(13),
	/**
	 * extern opencog::Type FALSE_LINK;
	 */
	FALSE_LINK(14),
	/**
	 * extern opencog::Type TRUE_LINK;
	 */
	TRUE_LINK(15),
	/**
	 * extern opencog::Type CONTEXT_LINK;
	 */
	CONTEXT_LINK(16),
	/**
	 * extern opencog::Type VARIABLE_NODE;
	 */
	VARIABLE_NODE(17),
	/**
	 * extern opencog::Type VARIABLE_TYPE_NODE;
	 */
	VARIABLE_TYPE_NODE(18),
	/**
	 * extern opencog::Type TYPED_VARIABLE_LINK;
	 */
	TYPED_VARIABLE_LINK(19),
	/**
	 * extern opencog::Type BIND_LINK;
	 */
	BIND_LINK(20),
	/**
	 * extern opencog::Type FORALL_LINK;
	 */
	FORALL_LINK(21),
	/**
	 * extern opencog::Type EXISTS_LINK;
	 */
	EXISTS_LINK(22);
	/**
	 * extern opencog::Type AVERAGE_LINK;
	 */
	/**
	 * extern opencog::Type SCHOLEM_LINK;
	 */
	/**
	 * extern opencog::Type IMPLICATION_LINK;
	 */
	/**
	 * extern opencog::Type EVALUATION_LINK;
	 */
	/**
	 * extern opencog::Type ASSOCIATIVE_LINK;
	 */
	/**
	 * extern opencog::Type INHERITANCE_LINK;
	 */
	/**
	 * extern opencog::Type SIMILARITY_LINK;
	 */
	/**
	 * extern opencog::Type EXTENSIONAL_SIMILARITY_LINK;
	 */
	/**
	 * extern opencog::Type INTENSIONAL_INHERITANCE_LINK;
	 */
	/**
	 * extern opencog::Type INTENSIONAL_SIMILARITY_LINK;
	 */
	/**
	 * extern opencog::Type PROCEDURE_NODE;
	 */
	/**
	 * extern opencog::Type GROUNDED_PROCEDURE_NODE;
	 */
	/**
	 * extern opencog::Type SCHEMA_NODE;
	 */
	/**
	 * extern opencog::Type GROUNDED_SCHEMA_NODE;
	 */
	/**
	 * extern opencog::Type PREDICATE_NODE;
	 */
	/**
	 * extern opencog::Type GROUNDED_PREDICATE_NODE;
	 */
	/**
	 * extern opencog::Type SATISFYING_SET_LINK;
	 */
	/**
	 * extern opencog::Type SCHEMA_EXECUTION_LINK;
	 */
	/**
	 * extern opencog::Type SCHEMA_EVALUATION_LINK;
	 */
	/**
	 * extern opencog::Type EXECUTION_LINK;
	 */
	/**
	 * extern opencog::Type EXECUTION_OUTPUT_LINK;
	 */
	/**
	 * extern opencog::Type PREDICTIVE_IMPLICATION_LINK;
	 */
	/**
	 * extern opencog::Type TAIL_PREDICTIVE_IMPLICATION_LINK;
	 */
	/**
	 * extern opencog::Type SEQUENTIAL_AND_LINK;
	 */
	/**
	 * extern opencog::Type SIMULTANEOUS_AND_LINK;
	 */
	/**
	 * extern opencog::Type EVENTUAL_SEQUENTIAL_AND_LINK;
	 */
	/**
	 * extern opencog::Type EVENTUAL_PREDICTIVE_IMPLICATION_LINK;
	 */
	/**
	 * extern opencog::Type HYPOTHETICAL_LINK;
	 */
	/**
	 * extern opencog::Type MIXED_IMPLICATION_LINK;
	 */
	/**
	 * extern opencog::Type EXTENSIONAL_IMPLICATION_LINK;
	 */
	/**
	 * extern opencog::Type EQUIVALENCE_LINK;
	 */
	/**
	 * extern opencog::Type SIMULTANEOUS_EQUIVALENCE_LINK;
	 */
	/**
	 * extern opencog::Type EXTENSIONAL_EQUIVALENCE_LINK;
	 */
	/**
	 * extern opencog::Type ANCHOR_NODE;
	 */
	/**
	 * extern opencog::Type COUNT_LINK;
	 */
	/**
	 * extern opencog::Type WORD_NODE;
	 */
	/**
	 * extern opencog::Type REFERENCE_LINK;
	 */
	/**
	 * extern opencog::Type DOCUMENT_NODE;
	 */
	/**
	 * extern opencog::Type SENTENCE_NODE;
	 */
	/**
	 * extern opencog::Type PARSE_NODE;
	 */
	/**
	 * extern opencog::Type PARSE_LINK;
	 */
	/**
	 * extern opencog::Type WORD_INSTANCE_NODE;
	 */
	/**
	 * extern opencog::Type WORD_INSTANCE_LINK;
	 */
	/**
	 * extern opencog::Type FEATURE_NODE;
	 */
	/**
	 * extern opencog::Type FEATURE_LINK;
	 */
	/**
	 * extern opencog::Type LINK_GRAMMAR_RELATIONSHIP_NODE;
	 */
	/**
	 * extern opencog::Type LINK_GRAMMAR_DISJUNCT_NODE;
	 */
	/**
	 * extern opencog::Type DEFINED_LINGUISTIC_CONCEPT_NODE;
	 */
	/**
	 * extern opencog::Type DEFINED_LINGUISTIC_RELATIONSHIP_NODE;
	 */
	/**
	 * extern opencog::Type PREPOSITIONAL_RELATIONSHIP_NODE;
	 */
	/**
	 * extern opencog::Type DEFINED_FRAME_NODE;
	 */
	/**
	 * extern opencog::Type DEFINED_FRAME_ELEMENT_NODE;
	 */
	/**
	 * extern opencog::Type FRAME_ELEMENT_LINK;
	 */
	/**
	 * extern opencog::Type WORD_SENSE_NODE;
	 */
	/**
	 * extern opencog::Type WORD_SENSE_LINK;
	 */
	/**
	 * extern opencog::Type PART_OF_SPEECH_NODE;
	 */
	/**
	 * extern opencog::Type PART_OF_SPEECH_LINK;
	 */
	/**
	 * extern opencog::Type LEMMA_NODE;
	 */
	/**
	 * extern opencog::Type LEMMA_LINK;
	 */
	/**
	 * extern opencog::Type HOLONYM_LINK;
	 */
	/**
	 * extern opencog::Type COSENSE_LINK;
	 */
	/**
	 * extern opencog::Type SEME_NODE;
	 */
	/**
	 * extern opencog::Type SEMANTIC_RELATION_NODE;
	 */
	/**
	 * extern opencog::Type AGISIM_SOUND_NODE;
	 */
	/**
	 * extern opencog::Type AGISIM_TASTE_NODE;
	 */
	/**
	 * extern opencog::Type AGISIM_SMELL_NODE;
	 */
	/**
	 * extern opencog::Type AGISIM_SELF_NODE;
	 */
	/**
	 * extern opencog::Type AGISIM_PERCEPT_NODE;
	 */
	/**
	 * extern opencog::Type AGISIM_VISUAL_PERCEPT_NODE;
	 */
	/**
	 * extern opencog::Type AGISIM_PIXEL_PERCEPT_NODE;
	 */
	/**
	 * extern opencog::Type AGISIM_POLYGON_PERCEPT_NODE;
	 */
	/**
	 * extern opencog::Type AGISIM_OBJECT_PERCEPT_NODE;
	 */
	/**
	 * extern opencog::Type CW_PIXEL_PERCEPT_NODE;
	 */
	/**
	 * extern opencog::Type CW_COLOR_NODE;
	 */
	/**
	 * extern opencog::Type FW_VARIABLE_NODE;
	 */
	/**
	 * extern opencog::Type LATEST_LINK;
	 */
	/**
	 * extern opencog::Type OBJECT_NODE;
	 */
	/**
	 * extern opencog::Type PET_NODE;
	 */
	/**
	 * extern opencog::Type AVATAR_NODE;
	 */
	/**
	 * extern opencog::Type STRUCTURE_NODE;
	 */
	/**
	 * extern opencog::Type ACCESSORY_NODE;
	 */
	/**
	 * extern opencog::Type HUMANOID_NODE;
	 */
	/**
	 * extern opencog::Type UNKNOWN_OBJECT_NODE;
	 */
	/**
	 * extern opencog::Type AT_TIME_LINK;
	 */
	/**
	 * extern opencog::Type TIME_NODE;
	 */
	/**
	 * extern opencog::Type IS_ACCEPTABLE_SECOND_ARG_LINK;
	 */
	/**
	 * extern opencog::Type HEBBIAN_LINK;
	 */
	/**
	 * extern opencog::Type ASYMMETRIC_HEBBIAN_LINK;
	 */
	/**
	 * extern opencog::Type SYMMETRIC_HEBBIAN_LINK;
	 */
	/**
	 * extern opencog::Type INVERSE_HEBBIAN_LINK;
	 */
	/**
	 * extern opencog::Type SYMMETRIC_INVERSE_HEBBIAN_LINK;
	 */

	private long id = 0;
	
	private AtomTypes(long id) {
		this.id = id;
	}
	
	public long getId() {
		return id;
	}
	
	public Type getType() {
		Type type = new Type();
		type.set(id);
		return type;
	}
}
