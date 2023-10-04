//
// Created by Dmitryb on 07.02.2023.
//

#include <iostream>

using namespace std;

int day(int ans, int b, int c, int d) {
    ans *= b;
    ans > c ? ans -= c : ans = 0;
    if (ans > d) ans = d;
    return ans;
}

int main() {
    int a, b, c, d, cnt = 0;
    long k;
    cin >> a >> b >> c >> d >> k;
    int ans = a;

    if (a > a * b - c) {
        while (ans > 0 && cnt < k) {
            ans = day(ans, b, c, d);
            cnt++;
        }
        if (ans < 0) ans = 0;
    }

    if (a < a * b - c) {
        while (ans < d && cnt < k) {
            ans = day(ans, b, c, d);
            cnt++;
        }
        if (ans > d) ans = d;
    }
    cout << ans;
    return 0;
}
