#include <jni.h>
#include "../../../include/opencog/atomspace/AtomSpace.h"
#include "../../../include/opencog/server/CogServer.h"
#include "../../../include/opencog/server/BaseServer.h"
#include "../../../native_lib/linux/target/jni_pln-javah/jni_pln.h"

#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT jobject JNICALL Java_fi_neter_opencog_server_CogServer_getAtomSpace
  (JNIEnv * env, jclass cls) {
	AtomSpace* as = BaseServer::getAtomSpace();

	// Creating the Java side wrapper for the created object.
	jclass asCls = env->FindClass("fi/neter/opencog/atomspace/AtomSpace");
	jobject asObj = env->AllocObject(asCls);
	jfieldID fid = env->GetFieldID(asCls, "handle", "J");
	env->SetLongField(asObj, fid, (jlong)as);
	return asObj;
}

#ifdef __cplusplus
}
#endif
