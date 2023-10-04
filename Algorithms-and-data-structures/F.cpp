//
// Created by Dmitryb on 23.03.2023.
//

#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

bool custom_comparator (const string& a, const string& b) {
    return a + b > b + a;
}

int main() {
    vector<string> parts;
    string s, res;
    while (cin >> s)
        parts.push_back(s);
    sort(parts.begin(), parts.end(), custom_comparator);
    for (const auto & part : parts)
        res += part;
    cout << res.c_str() << endl;
    return 0;
}