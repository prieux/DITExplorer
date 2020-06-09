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

public final class DITExplorerParameters {
	private static DITExplorerParameters instance = null;
	private String pathOfJars;
	private String outputFilePath;
	private String fromClassPath;
	
	public static synchronized DITExplorerParameters getInstance() {
		if (instance == null) {
			instance = new DITExplorerParameters();
		}
		return instance;
	}
	
	public void setParameters(String[] args) {
		this.pathOfJars = args[0];
		this.outputFilePath = args[1];
		this.fromClassPath = args[2];
	}
	
	public void setParameters(String inputPath, String outputFile, String fromClassPath) {
		this.pathOfJars = inputPath;
		this.outputFilePath = outputFile;
		this.fromClassPath = fromClassPath;
	}
	
	public String getPathOfJars() {
		return pathOfJars;
	}
	
	public String getOutputFilePath() {
		return outputFilePath;
	}
	
	public String getFromClassPath() {
		return fromClassPath;
	}
}
