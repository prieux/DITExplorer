/*
 This program generates a JSON file that can be used to visualize A Depth
 Inheritance Tree (DIT) of a bunch of jar files, residing in a directory
 given the path directory as a input parameter.

 Copyright (C) 2014  Pascal Rieux

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.

    Author: Pascal Rieux, pascalrieux64@gmail.com
 */

package net.prieux.javatools.ditexplorer;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class DITExplorer {

    private ClassLoader classLoader;

    /**
     * @param args
     */
    public static void main(String[] args) {
        DITExplorer ditExplorer = new DITExplorer();
        DITExplorerParameters.getInstance().setParameters(args);
        try {
            ditExplorer.process();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void process() throws IOException {
        displayLicense();
        DITExplorerParameters params = DITExplorerParameters.getInstance();
        List<String> jarPathList = getJarPathListAndInitClassLoader(params.getPathOfJars());
        ClassGraph classGraph = analyzeClassInheritance(jarPathList, params.getFromClassPath());
        classGraph.toJSON(params.getOutputFilePath(), true);
        System.out.println("done.");
    }

    private void displayLicense() {
        System.out.println("DITExplorer Copyright (C) 2014, Pascal Rieux");
        System.out.println("This program comes with ABSOLUTELY NO WARRANTY;");
        System.out.println("This is free software, and you are welcome to redistribute it");
        System.out.println("under certain conditions; See LICENSE.txt for details.");
        System.out.println("");
    }

    private List<String> getJarPathListAndInitClassLoader(String pathOfJars) throws IOException {
        System.out.println("Loading jar files...");
        Path directory = FileSystems.getDefault().getPath(pathOfJars);
        DirectoryStream<Path> ds = Files.newDirectoryStream(directory);
        List<URL> jarUrlList = new ArrayList<URL>();
        List<String> jarPathList = new ArrayList<String>();
        for (Path child : ds) {
            String jarPathName = directory + "\\" + child.getFileName();
            jarPathList.add(jarPathName);
            File jarAsFile = new File(jarPathName);
            jarUrlList.add(jarAsFile.toURI().toURL());
            System.out.print(".");
        }
        URL[] jarUrls = jarUrlList.toArray(new URL[jarUrlList.size()]);
        classLoader = new URLClassLoader(jarUrls);
        System.out.println("");
        return jarPathList;
    }

    private ClassGraph analyzeClassInheritance(List<String> jarPathList, String fromClassPath) throws IOException {
        System.out.println("Analyzing class inheritance...");
        ClassGraph classGraph = new ClassGraph(fromClassPath);
        for (String jarPathName : jarPathList) {
            JarFile jarFile = new JarFile(jarPathName);
            Enumeration<JarEntry> jarEntries = jarFile.entries();
            while (jarEntries.hasMoreElements()) {
                JarEntry jarEntry = (JarEntry) jarEntries.nextElement();
                if (jarEntry.isDirectory()) {
                    continue;
                }
                String className = jarEntry.getName().replace('/', '.').replaceAll("\\.class", "");
                if (className.startsWith(fromClassPath)) {
                    insertClassNode(classGraph, className);
                    System.out.print(".");
                }
            }
            jarFile.close();
        }
        System.out.println("");
        return classGraph;
    }

    private void insertClassNode(ClassGraph classGraph, String className) {
        try {
            Class<?> classEntry = classLoader.loadClass(className);
            classGraph.insert(classEntry);
        } catch (ClassNotFoundException e) {
            // do nothing, the expected jar might not be in the source directory.
        } catch (NoClassDefFoundError e) {
            // do nothing
        }
    }
}
