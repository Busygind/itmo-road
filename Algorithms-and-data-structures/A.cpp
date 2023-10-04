#include <iostream>
#include <vector>

using namespace std;

int main() {
    int n, buf;
    int prev = -1, max = 1, cur = 1, cur_cnt = 1, left = 0;
    pair<int, int> ans;

    cin >> n;
    for (int i = 0; i < n; i++) {
        cin >> buf;
        if (prev == -1) {
            prev = buf;
            continue;
        }
        if (buf == prev) {
            cur_cnt++;
        } else {
            cur_cnt = 1;
        }
        if (cur_cnt == 3) {
            if (max < cur) {
                max = cur;
                ans.first = left;
                ans.second = i - 1;
            }
            left = i - 1;
            cur_cnt = 2;
            cur = 1;
        }
        cur++;
        prev = buf;
    }
    if (max < cur) {
        ans.first = left;
        ans.second = n - 1;
    }
    cout << ans.first + 1 << " " << ans.second + 1 << endl;
    return 0;
}
