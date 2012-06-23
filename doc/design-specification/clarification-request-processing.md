# Clarification request processing specification.

 1. When system needs help with understanding, see
[DoNotUnderstand](https://github.com/development-team/2/blob/master/doc/informal/design-specification.md#Activity_diagram) sub-process.

 1. It stores the reference to clarification request subject node of model SemanticNetwork. This node defines the concept or template of an expected response.

 1. There could be several types of reference:

   2. Lack of information reference = System miss mandatory information.
   2. Lack of understanding reference = System do not understand the term used in inbound Incident description or Clarification Request Response.
   2. SemanticNetwork structure does not make sense = System domain model do not mach the SemanticNetwork of inbound Incident description or Clarification Request Response

 1. System creates the Clarification Request and sends it to a User. Clarification Request contains Context KLine URI.

 1. User creates Clarification Request Response.

 1. System runs Annotation process over the Clarification Response, then tries to match SemanticNetwork of Clarification Response with the SemanticNetwork of clarification request subject reference via Simulation Way2Think.

 1. If inbound Response matches expected clarification request subject, System updates Incident description SemanticNetwork.

 1. If inbound Response does not match System rises Clarification Request over Clarification Response.