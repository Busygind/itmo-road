//
// Created by Dmitryb on 05.04.2023.
//

#include <iostream>
#include <algorithm>

using namespace std;

int main() {
    int n, loc_min = 1000000000, res = 0, x;
    cin >> n;
    for (int i = 0; i < n; i++) {
        for (int j = 0; i < n; i++) {
            cin >> x;
            if (i == j) continue;
            if (x < loc_min) loc_min = x;
        }
        if (loc_min > res) res = loc_min;
    }
    cout << res;
    return 0;
}