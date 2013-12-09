Author: Florian Blume, fblume1@sheffield.ac.uk

#####################################

General information on the assignment:

## Dependencies:
Jogl2
Textures
(both delivered)

## Altered existing classes:
Assignment (Tex3)
AssignmentScene (Tex3Scene)
ProceduralMeshFactory

## Additional task implemented:
4 spotlights (4 lamps that have spotlights as lights) creating pools of light in the room
(not on the ceiling because I thought it would be nice to have 4 lamps that create those pools)

## To the structure of the programme:

It may seem as if there are quite a lot of classes for this small scene. However, I tried to
make the programme as generic and reusable as possible, and created most of the classes in the
object package for this reason.

The tree structure is achieved through the class ObjectPart, which allows to stack multiple
ObjectParts into each other, which then can be individually as well as globally (by the parent
ObjectPart) altered.

Also, the animation is out-sourced into special classes to make it possible to easily animate
new objects (e.g. the rolling barrel).

At first glance the structure may be rather difficult to understand, but once used to it, the
creation of own animations and new objects is easy and the structure therefore a powerful tool.

#####################################

How to run:

The needed commands are all stored in the compile_and_run.bat. After the first run of this batch
file, run.bat can be used to launch the programme again.