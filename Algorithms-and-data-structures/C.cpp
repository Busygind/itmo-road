//
// Created by Dmitryb on 19.02.2023.
//

#include <bits/stdc++.h>
#include <iostream>
#include <string>
#include <vector>
#include <algorithm>

using namespace std;

bool isNumber(const string& s) {
    return all_of(s.begin(), s.end(),
                  [](char c){ return isdigit(c) != 0 || c == '-'; });
}

int main() {
    unordered_map<string, vector<string>> m;
    vector<vector<string>> cur_layer {{}};
    string s;
    while (getline(cin, s)) {
        if (s == "{") cur_layer.emplace_back();
        else if (s == "}") {
            for (const auto & i : cur_layer.back())
                m[i].pop_back();
            cur_layer.pop_back();
        } else {
            int i = s.find('=');
            string preS = s.substr(0, i);
            string postS = s.substr(i + 1);
            if (isNumber(postS)) {
                if (m.find(preS) == m.end()) {
                    m.insert({preS, {postS}});
                    cur_layer.back().push_back(preS);
                } else {
                    m[preS].push_back(postS);
                    cur_layer.back().push_back(preS);
                }
            } else {
                if (m.find(postS) == m.end()) {
                    m.insert({postS, {"0"}});
                    cur_layer.back().push_back(postS);
                } else if (m.at(postS).empty()) {
                    m[postS].emplace_back("0");
                    cur_layer.back().push_back(postS);
                }
                if (m.find(preS) == m.end()) {
                    m.insert({preS, {m[postS].back()}});
                    cur_layer.back().push_back(preS);
                } else {
                    m[preS].push_back(m[postS].back());
                    cur_layer.back().push_back(preS);
                }
                cout << m[preS].back() << endl;
            }
        }
    }
    return 0;
}