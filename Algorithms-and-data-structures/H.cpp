//
// Created by Dmitryb on 14.02.2023.
//

#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

int main() {
    int n, k, ans = 0;
    cin >> n >> k;
    vector<int> a(n);
    for (int i = 0; i < n; ++i) {
        cin >> a[i];
    }
    sort(a.rbegin(), a.rend());
    for (int i = 0; i < n; i++) {
        if (i % k != k - 1) ans += a[i];
    }
    cout << ans;
    return 0;
}