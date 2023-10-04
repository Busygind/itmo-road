//
// Created by Dmitryb on 14.02.2023.
//

#include <iostream>
#include <vector>

using namespace std;

bool valid(int a, vector<int>& arr, int k) {
    int cnt = 1;
    int last = arr[0];
    for (int elem : arr)
        if (elem - last >= a) {
            cnt++;
            last = elem;
        }

    return cnt >= k;
}

void binSearch(int l, int r, vector<int>& arr, int k) {
    while (l < r) {
        int m = (l + r + 1) / 2; // right bin search
        if (valid(m, arr, k))
            l = m;
        else
            r = m - 1;
    }
    cout << l;
}

int main() {
    int n, k;
    cin >> n >> k;
    vector<int> arr (n);
    for (int i = 0; i < n; i++) {
        cin >> arr[i];
    }
    binSearch(0, arr[n - 1], arr, k);
}

