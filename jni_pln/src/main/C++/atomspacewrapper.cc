#include <jni.h>
#include "../../../include/opencog/reasoning/pln/AtomSpaceWrapper.h"
#include "../../../native_lib/linux/target/jni_pln-javah/jni_pln.h"

#ifdef __cplusplus
extern "C" {
#endif


JNIEXPORT jobject JNICALL Java_fi_neter_opencog_reasoning_pln_AtomSpaceWrapper_getAsw
  (JNIEnv * env, jclass cls) {
	// Getting the AtomSpaceWrapper.
	AtomSpaceWrapper* asw = GET_ASW;

	// Creating the Java side wrapper for the created object.
	jobject obj = env->AllocObject(cls);
	jfieldID fid = env->GetFieldID(cls, "handle", "J");
	env->SetLongField(obj, fid, (jlong)asw);
	return obj;
}

JNIEXPORT jobject JNICALL Java_fi_neter_opencog_reasoning_pln_AtomSpaceWrapper_getAtomSpace
  (JNIEnv * env, jobject obj) {
	// We need to call native getAtomSpace and wrap the result into a Java object.

	// Getting the native object.
	jclass cls = env->GetObjectClass(obj);
	jfieldID fid = env->GetFieldID(cls, "handle", "J");
	AtomSpaceWrapper* asw = (AtomSpaceWrapper*)env->GetLongField(obj, fid);

	AtomSpace* as = asw->getAtomSpace();

	// Wrapping.

	// Creating the Java side wrapper for the created object.

	jclass asCls = env->FindClass("fi/neter/opencog/atomspace/AtomSpace");
	jobject asObj = env->AllocObject(asCls);
	jfieldID asFid = env->GetFieldID(asCls, "handle", "J");
	env->SetLongField(asObj, asFid, (jlong)as);
	return asObj;
}

#ifdef __cplusplus
}
#endif
