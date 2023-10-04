//
// Created by Dmitryb on 06.04.2023.
//

#include <iostream>
#include <vector>
#include <queue>
#include <set>

using namespace std;

int main() {
    int n, m, request;
    cin >> n >> m;

    vector<pair<int, int>> requests;
    priority_queue<pair<int, int>> queue;
    queue.emplace( n, 1 );

    multiset<int> vacant;

    vacant.insert(1);
    vacant.insert(n);
    for (int i = 0; i < m; ++i) {
        cin >> request;
        if (request <= 0) {
            request = -request;
            requests.emplace_back(0, 0);
            if (requests[request - 1].first != 0) {
                vacant.insert(requests[request - 1].second);
                vacant.insert(requests[request - 1].second + requests[request - 1].first - 1);
                if (vacant.find(requests[request - 1].second - 1) != vacant.end()) {
                    vacant.erase(vacant.find(requests[request - 1].second - 1));
                    vacant.erase(vacant.find(requests[request - 1].second));
                }
                if (vacant.find(requests[request - 1].second + requests[request - 1].first) != vacant.end()) {
                    vacant.erase(vacant.find(requests[request - 1].second + requests[request - 1].first));
                    vacant.erase(vacant.find(requests[request - 1].second + requests[request - 1].first - 1));
                }
                auto add = vacant.lower_bound(requests[request - 1].second);
                if (*add != requests[request - 1].second) {
                    int right = *add;
                    --add;
                    queue.emplace(right - *add + 1, *add);
                } else {
                    int left = *add;
                    ++add;
                    queue.emplace(*add - left + 1, left);
                }
                requests[request - 1].first = 0;
            }
        } else {

            auto front = queue.top();
            queue.pop();

            int upper = n;
            auto add = vacant.find(front.second);
            if (add != vacant.end() && ++add != vacant.end()) upper = *add;
            while (!queue.empty() && (vacant.find(front.second) == vacant.end() || upper - front.second + 1 != front.first)) {
                front = queue.top();
                queue.pop();
                upper = n;
                add = vacant.find(front.second);
                if (add != vacant.end() && ++add != vacant.end()) {
                    upper = *add;
                }
            }
            if (front.first >= request && vacant.find(front.second) != vacant.end() && upper - front.second + 1 == front.first) {
                cout << front.second << endl;
                requests.emplace_back(request, front.second);
                vacant.erase(vacant.find(front.second));
                front.first == request ? vacant.erase(vacant.find(front.second + request - 1)) : vacant.insert(front.second + request);
                queue.emplace(front.first - request, front.second + request);
            } else {
                cout << -1 << endl;
                requests.emplace_back(0, 0);
                queue.push(front);
            }
        }
    }
}