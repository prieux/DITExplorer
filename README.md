DITExplorer
===========

Summary
-------

This program generates a JSON file that can be used to visualize A Depth
Inheritance Tree (DIT) of a bunch of jar files, residing in a directory
given the path directory as a input parameter.

Usage
-----

    java net.prieux.javatools.ditexplorer.DITExplorer C:\Users\prieux\workspace\DITExplorer\target\ C:\Users\prieux\workspace\DITExplorer\output\dit.json net.prieux.javatools
    
will analyse jar files residing in `C:\Users\prieux\workspace\DITExplorer\target\` and output the `C:\Users\prieux\workspace\DITExplorer\output\dit.json` file that can be e.g. further used along within the `class_graph.html` file (based on sigmajs API and examples, see [sigmajs](http://sigmajs.org/)).
 