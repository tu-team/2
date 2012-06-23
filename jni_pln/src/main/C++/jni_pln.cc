/*
 * jni_pln.cc
 *
 *  Created on: Mar 31, 2012
 *      Author: tero
 */
/* Header for class fi_neter_JNIPLN */

#ifndef _Included_fi_neter_JNIPLN
#define _Included_fi_neter_JNIPLN

#include "../../../include/opencog/server/CogServer.h"

#include "atomspace.cc"
#include "atomspacewrapper.cc"
#include "truthvalue.cc"
#include "cogserver.cc"

/*
 * FIXME: Create destroyers for all allocated objects here, and call them from the Java side destroy() methods.
 */

#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT jobject JNICALL Java_fi_neter_JNIPLN_cogServer
(JNIEnv * env, jclass cls) {
	CogServer& cogServer = cogserver();
	// Creating the Java side wrapper for the created object.
	jclass asCls = env->FindClass("fi/neter/opencog/server/CogServer");
	jobject obj = env->AllocObject(asCls);
	jfieldID fid = env->GetFieldID(asCls, "handle", "J");
	env->SetLongField(obj, fid, (jlong)&cogServer);
	return obj;
}

JNIEXPORT jobject JNICALL Java_fi_neter_JNIPLN_ASW
  (JNIEnv * env, jclass cls, jobject obj) {
	jclass asCls = env->FindClass("fi/neter/opencog/atomspace/AtomSpace");
	jfieldID handleFid = env->GetFieldID(asCls, "handle", "J");
	AtomSpace* as = (AtomSpace*) env->GetLongField(obj, handleFid);

	AtomSpaceWrapper* asw = ASW(as);

	// Creating the Java side wrapper for the created object.
	jclass aswCls = env->FindClass("fi/neter/opencog/reasoning/pln/AtomSpaceWrapper");
	jobject aswObj = env->AllocObject(aswCls);
	jfieldID fid = env->GetFieldID(aswCls, "handle", "J");
	env->SetLongField(aswObj, fid, (jlong)asw);
	return aswObj;
}

#ifdef __cplusplus
}
#endif


#endif
