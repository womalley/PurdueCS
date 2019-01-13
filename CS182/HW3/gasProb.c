/*
                A driver wants to go from Loc1 to Loc2 with his car. The car tank can hold enough gas to travel
        d miles. The driver has a map that gives the distances between gas stations on his route. The distance between
        any consecutive stations is ≤ d. The drivers wants to make as few stops as possible.

                Find an efficient algorithm to find which gas stations to stop. The input Dist[1, . . . , n−1] is an array
        of n − 1 values, where n = total number of gas stations, D[i] is the distance from gas station Gi to gas station
        Gi+1.

*/

#include<stdio.h>

void main() {

        int milesLeft = 823; //miles till destination is reached
        int n = 12; //number of gas stations between loc1 and loc2
        int D[12] = {43, 65, 42, 87, 104, 98, 43, 71, 56, 111, 80, 23}; //array of all gas station distances
        int max = 215; //max gas capacity in miles
        int d = max; //miles left in gas tank
        int i;
        int refill = 0;


        for(i = 0; i < n; i++) {

                if (d < D[i]) {
                        //refill
                        d = max - D[i];
                        milesLeft = milesLeft - D[i];
                        refill++;
                        printf("Stop at gas station #%d\n", i);
                }
                else {
                        milesLeft = milesLeft - D[i];
                        d = d - D[i];
                }
        }
        printf("\nTotal number of stops: %d\n and i: %d", refill, i);
}


