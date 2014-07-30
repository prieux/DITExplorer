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

public class ClassEdge {
	private ClassNode source;
	private ClassNode target;
	private long id;
	
	public ClassEdge(ClassNode source, ClassNode target) {
		this.source = source;
		this.target = target;
	}
	
	public boolean isClassNodeSourceOrTarget(ClassNode classNode) {
		return (source.equals(classNode) || target.equals(classNode));
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String toJSON() {
		return "{\"source\":\"" + source.getId() + "\",\"target\":\"" + target.getId() + "\",\"id\":\"" + id + "\"}";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((source == null) ? 0 : source.hashCode());
		result = prime * result + ((target == null) ? 0 : target.hashCode());
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
		ClassEdge other = (ClassEdge) obj;
		if (source == null) {
			if (other.source != null) {
				return false;
			}
		} else if (!source.equals(other.source)) {
			return false;
		}
		if (target == null) {
			if (other.target != null) {
				return false;
			}
		} else if (!target.equals(other.target)) {
			return false;
		}
		return true;
	}
}
