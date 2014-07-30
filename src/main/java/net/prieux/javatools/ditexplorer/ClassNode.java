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

import java.util.ArrayList;
import java.util.List;

public class ClassNode {
	private String name;
	private List<ClassNode> children;
	private long id;
	private String label;
	
	public ClassNode(String classNodeName) {
		this.name = classNodeName;
		children = new ArrayList<ClassNode>();
	}
	
	public void addChild(ClassNode child) {
		children.add(child);
	}
	
	public boolean hasChild(ClassNode child) {
		return children.contains(child);
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public long getId() {
		return id;
	}
	
	public boolean hasChildren() {
		return !children.isEmpty();
	}
	
	public List<ClassNode> getChildren() {
		return children;
	}
	
	public void buildLabel(String fromClassPath, String shortClassPath) {
		this.label = name.replaceAll(fromClassPath, shortClassPath);
	}
	
	public String toJSON() {
		return "{\"label\":\"" + label + "\",\"id\":\"" + id + "\"}";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ClassNode other = (ClassNode) obj;
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "ClassNode [name=" + label + ", id=" + id + ", children=" + children + "]";
	}
}