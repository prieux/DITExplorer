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

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class ClassGraph {
	private List<ClassNode> classNodes;
	private List<ClassEdge> classEdges;
	private String fromClassPath;
	private String shortClassPath;
	
	public ClassGraph(String fromClassPath) {
	    classNodes = new ArrayList<ClassNode>();
	    classEdges = new ArrayList<ClassEdge>();
	    this.fromClassPath = fromClassPath;
	    this.shortClassPath = buildShortClassPath(fromClassPath);
	}
	
	protected String buildShortClassPath(String longClassPath) {
		String[] elements = longClassPath.split("\\.");
	    String shortClassPath = "";
		for (String element : elements) {
			shortClassPath += element.charAt(0) + ".";
		}
		int lastDotIndex = shortClassPath.lastIndexOf('.');
		if (lastDotIndex > 1) {
			shortClassPath = shortClassPath.substring(0, lastDotIndex);
		}
		else {
			shortClassPath = longClassPath.substring(0, 1);
		}
		return shortClassPath;
	}
	
	public void insert(Class<?> classEntry) {
		Class<?> currentClass = classEntry;
		Class<?> superClass = currentClass.getSuperclass();
		boolean exit = currentClass.isInterface();
		while (!exit) {
			ClassNode currentClassNode = addClassNode(currentClass.getName());
			ClassNode parentClassNode = addClassNode(superClass.getName());
			parentClassNode.addChild(currentClassNode);
			
			ClassEdge classEdge = new ClassEdge(parentClassNode, currentClassNode);
			if (!classEdges.contains(classEdge)) {
				classEdges.add(classEdge);
				classEdge.setId(classEdges.indexOf(classEdge));
			}
			
			currentClass = superClass;
			superClass = currentClass.getSuperclass();
			exit = superClass == null;
		}
	}
	
	private ClassNode addClassNode(String className) {
		ClassNode classNodeToAdd = new ClassNode(className);
		int index = classNodes.indexOf(classNodeToAdd);
		if (index == -1) {
			classNodes.add(classNodeToAdd);
			classNodeToAdd.setId(classNodes.indexOf(classNodeToAdd));
			classNodeToAdd.buildLabel(fromClassPath, shortClassPath);
		} else {
			classNodeToAdd = classNodes.get(index);
		}
		return classNodeToAdd;
	}
	
	public void toJSON(String outputFilePath, boolean noDescendantOfObjectHavingNoChild) {
		System.out.println(classNodes.size() + " nodes before deletion");
		System.out.println(classEdges.size() + " edges before deletion");
		if (noDescendantOfObjectHavingNoChild) {
			removeDescendantsOfObjectHavingNoChild();
		}
		System.out.println(classNodes.size() + " nodes after deletion");
		System.out.println(classEdges.size() + " edges after deletion");
		try {
			PrintWriter writer = new PrintWriter(new FileWriter(outputFilePath));
			writer.println("{\"nodes\":[");
			int i = 0;
			for (i = 0 ; i < classNodes.size() - 1 ; i++) {
				writer.print(classNodes.get(i).toJSON() + "," );
			}
			writer.print(classNodes.get(i).toJSON());
			writer.println("],\"edges\":[");
			for (i = 0 ; i < classEdges.size() - 1 ; i++) {
				writer.print(classEdges.get(i).toJSON() + "," );
			}
			writer.print(classEdges.get(i).toJSON());
			writer.println("]}");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void removeDescendantsOfObjectHavingNoChild() {
		ClassNode rootClass = classNodes.get(classNodes.indexOf(new ClassNode("java.lang.Object")));
		List<ClassNode> rootChildren = rootClass.getChildren();
		List<ClassNode> classNodesToDelete = new ArrayList<ClassNode>();
		for (ClassNode rootChild : rootChildren) {
			if (!rootChild.hasChildren()) {
				classNodesToDelete.add(rootChild);
			}
		}
		System.out.println(classNodesToDelete.size() + " nodes to delete");
		for (ClassNode nodeToDelete : classNodesToDelete) {
			deleteNode(nodeToDelete);
		}
	}
	
	private void deleteNode(ClassNode nodeToDelete) {
		// first delete in and out edges
		List<ClassEdge> edgesToDelete = new ArrayList<ClassEdge>();
		for (ClassEdge classEdge : classEdges) {
			if (classEdge.isClassNodeSourceOrTarget(nodeToDelete)) {
				edgesToDelete.add(classEdge);
			}
		}
		for (ClassEdge edgeToDelete : edgesToDelete) {
			classEdges.remove(edgeToDelete);
		}
		// finally delete node
		classNodes.remove(nodeToDelete);
	}
}
