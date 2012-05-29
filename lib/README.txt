Please install RelEx dependencies: http://wiki.opencog.org/w/RelEx_Install.

This is the library of all the jars that are not available in maven2 repository.
All jars are stored in the src/main/resources directory and are installed via mvn generate-resources command.

To add new jar please place it under the src/main/resources directory and add following plugin tag in pom.xml
(do not forget to change all parameters starting from MYLIB to the proper for your jar):

            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-install-plugin</artifactId>
                <version>2.2</version>
                <executions>
                    <execution>
                        <id>install-MYLIB</id>
                        <phase>generate-resources</phase>
                        <goals>
                            <goal>install-file</goal>
                        </goals>
                    </execution>
                </executions>
                <configuration>
                    <file>${lib.dir}/MYLIB.jar</file>
                    <groupId>MYLIB.group</groupId>
                    <artifactId>MYLIB.artifactId</artifactId>
                    <packaging>jar</packaging>
                    <version>MYLIB.version</version>
                </configuration>
            </plugin>