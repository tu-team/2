/*
 * Copyright 2001-2009 Artima, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import collection.Map
import tu.dataservice.knowledgebaseserver.N4JKB
import org.neo4j.graphdb.{Node, GraphDatabaseService, Transaction, RelationshipType}
import org.neo4j.kernel.EmbeddedGraphDatabase
import org.scalatest._
import org.scalatest.Assertions._


/*
Here's an example of a FunSuite with ShouldMatchers mixed in:
*/

import org.scalatest.matchers.ShouldMatchers

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner


 import java.io.FileReader
 import java.io.FileWriter
 import java.io.File


class TestRelation extends RelationshipType
{
  def name():String = {"test"}
}

@RunWith(classOf[JUnitRunner])
class KbTest extends FunSuite{
  test("empty ...")
  {
    val testRelation = new TestRelation
    var ok:Boolean = false


    assert(!ok)

    val tx:Transaction = N4JKB().beginTx();
    try
    {
      var indexedNode = N4JKB.addIndexedNode("node1")
      indexedNode.setProperty("test_property", "test_value")
      //val firstNode:Node = N4JKB.addLinkedNode(fromNode:Node, relation:RelationshipType)
      tx.success();
      ok = true
    }
    finally
    {
      tx.finish();
    }
    assert(ok)
    //dummy.graphDb.shutdown()

  }

}

@RunWith(classOf[JUnitRunner])
class N4JTest extends FunSuite{
  test("empty write to db")
  {
    val testRelation = new TestRelation
    var ok:Boolean = false
    val tx:Transaction = N4JKB().beginTx();
    try
    {
      val firstNode = N4JKB().createNode();
      firstNode.setProperty( "message", "Hello, " );
      val secondNode = N4JKB().createNode();
      secondNode.setProperty( "message", "World!" );
      val relationship = firstNode.createRelationshipTo( secondNode, testRelation );
      relationship.setProperty( "message", "brave Neo4j " );

      tx.success();
      ok = true
    }
    finally
    {
      tx.finish();
    }
    assert(ok)
    //dummy.graphDb.shutdown()

  }

}

@RunWith(classOf[JUnitRunner])
class N4JStressTest extends FunSuite{

}

@RunWith(classOf[JUnitRunner])
class MasterSuite extends Suite with BeforeAndAfterAll {

   // Set up the temp file needed by the test, taking
   // a file name from the configMap
   //override def beforeAll(configMap: Map[String, Any]) {
   override def beforeAll() {

//     dummy.graphDb = new EmbeddedGraphDatabase( "~/kb" )

   }

   override def nestedSuites =
     List(new KbTest, new N4JTest, new N4JStressTest)

   override def afterAll() {
     //     maybe remove all test data?
     //ToDo - find about multi thread for study
   }
 }


