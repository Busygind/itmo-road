//
// Created by Dmitryb on 05.04.2023.
//

#include <iostream>
#include <algorithm>
#include <bits/stdc++.h>

using namespace std;

int main() {
    int n, k;
    multiset<int> window;
    cin >> n >> k;
    int values[n];
    for (int i = 0; i < n; i++) cin >> values[i];
    for (int i = 0; i < k; i++) window.insert(values[i]);
    cout << *next(window.begin(), 0) << " ";
    for (int i = k; i < n; i++) {
        auto itr = window.find(values[i - k]);
        if (itr != window.end()) window.erase(itr);
        window.insert(values[i]);
        cout << *next(window.begin(), 0) << " ";
    }
    return 0;
}