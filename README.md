# PigeonsSquare

A passionating scene representing graphically the behavior of a multi-threaded application, including concurrential access.

Separate threads (graphically embodied by *pigeons*) live within a 2D-plane and implement two main behaviors:
* idle;
* reaching for food.

The user can spawn some food at an arbitrary situation by left-clicking onto the plane. Food has a fresh state for a random time (assigned upon creation), after whitch it becomes rottened, therefore becoming uninteresting to the pigeons.

Any pigeon has a random speed (assigned upon initialization), and is scanning the square at any time for food. Whenever it detects some food, it evaluates its distance to it and runs to it if it's the closest.

Eating food constitutes a concurrential access to food, which is therefore a synchronized process, as well as graphical rendering.

The application is *entirely generic* so that it is really easy and quick to add a species of pigeon with its own behavior.
