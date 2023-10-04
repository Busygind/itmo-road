//
// Created by Dmitryb on 23.03.2023.
//

#include <iostream>
#include <vector>
#include <algorithm>
#include <map>

using namespace std;

int c[26];

bool custom_comparator(pair<char, int> a, pair<char, int> b) {
    return c[a.first - 'a'] < c[b.first - 'a'];
}

int main() {
    string s, res;
    cin >> s;
    vector<pair<char, int>> counts;
    map<char, int> letters;
    for (int & i : c)
        cin >> i;
    for (char i : s)
        letters[i]++;
    for (auto & letter : letters) {
        counts.emplace_back(letter.first, letter.second);
    }
    sort(counts.begin(), counts.end(), custom_comparator);
    for (auto & count : counts) {
        if (count.second > 1) {
            res = count.first + res + count.first;
            count.second -= 2;
        }
    }
    s = "";
    for (auto & count : counts) {
        while (count.second > 0) {
            s += count.first;
            count.second--;
        }
    }
    res = res.substr(0, res.size() / 2) + s + res.substr(res.size()/2, res.size());

    cout << res << endl;
    return 0;
}