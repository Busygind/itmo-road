//
// Created by Dmitryb on 07.02.2023.
//

#include <iostream>
#include <string>
#include <stack>
#include <vector>
#include <map>

using namespace std;

int main() {
    stack<pair<char, int>> st; // symb + num in stack
    string s;
    int animals = 0, traps = 0;
    map<int, int> trapsIdx; // key - trap_idx, value - animal_idx
    cin >> s;
    for (char i: s) {
        if (st.empty()) {
            if (i > 96) {
                animals++;
                st.emplace(i, animals);
            } else {
                traps++;
                st.emplace(i, traps);
            }
            continue;
        }

        if (st.top().first == char(i + 32)) { // дошли до ловушки на животное в стеке
            traps++;
            trapsIdx[traps] = st.top().second;
            st.pop();
        } else if (st.top().first == char(i - 32)) { // дошли до животного на ловушку в стеке
            animals++;
            trapsIdx[st.top().second] = animals;
            st.pop();
        } else {
            if (i > 96) {
                animals++;
                st.emplace(i, animals);
            } else {
                traps++;
                st.emplace(i, traps);
            }
        }
    }
    if (st.empty()) {
        cout << "Possible" << endl;
        for (auto &it: trapsIdx)
            cout << it.second << " ";
    } else cout << "Impossible" << endl;
    return 0;
}
