#include <jni.h>
#include "../../../include/opencog/atomspace/TruthValue.h"
#include "../../../native_lib/linux/target/jni_pln-javah/jni_pln.h"

#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT jobject JNICALL Java_fi_neter_opencog_atomspace_TruthValue_TRUE_1TV
  (JNIEnv * env, jclass cls) {
	// If we don't clone this, the TruthValue is created to the stack and destroyed at the end of this method.
	TruthValue* tv = TruthValue::TRUE_TV().clone();

	jobject obj = env->AllocObject(cls);
	jfieldID fid = env->GetFieldID(cls, "handle", "J");
	env->SetLongField(obj, fid, (jlong)tv);
	return obj;
}

#ifdef __cplusplus
}
#endif
