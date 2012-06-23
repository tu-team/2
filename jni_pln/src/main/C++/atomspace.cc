#include <jni.h>
#include "../../../include/opencog/atomspace/AtomSpace.h"
#include "../../../native_lib/linux/target/jni_pln-javah/jni_pln.h"

#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT jobject JNICALL Java_fi_neter_opencog_atomspace_AtomSpace_construct
  (JNIEnv * env, jclass cls) {
	// Creating a new AtomSpace.
	opencog::AtomSpace* as = new opencog::AtomSpace();

	// Creating the Java side wrapper for the created object.
	jobject obj = env->AllocObject(cls);
	jfieldID fid = env->GetFieldID(cls, "handle", "J");
	env->SetLongField(obj, fid, (jlong)as);
	return obj;
}

#ifdef __cplusplus
}
#endif
