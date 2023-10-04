//
// Created by Dmitryb on 21.05.2023.
//

#include <iostream>
#include <vector>
#include <queue>

using namespace std;

bool breadth_first_search(const vector<vector<int>>& graph, int start,  vector<int>& parts, vector<bool>& visited) {
    queue<int> queue_to_visit;
    visited[start] = true;
    queue_to_visit.push(start);

    if (parts[start] == 0) {
        parts[start] = 1;
    }

    while (!queue_to_visit.empty()) {
        int cur = queue_to_visit.front();
        queue_to_visit.pop();

        for (int neighbour : graph[cur]) {
            if (!visited[neighbour]) {
                parts[cur] == 1 ? parts[neighbour] = 2 : parts[neighbour] = 1;
                queue_to_visit.push(neighbour);

                visited[neighbour] = true;
            } else if (parts[neighbour] == parts[cur]) return false;
        }
    }
    return true;
}

int main() {
    int n, m;
    cin >> n >> m;
    vector<vector<int>> graph(n);
    vector<bool> visited(n, false);
    vector<int> parts(n, 0);

    for (int i = 0; i < m; i++) {
        int first, second;
        cin >> first >> second;
        graph[first - 1].push_back(second - 1);
        graph[second - 1].push_back(first - 1);
    }

    bool success = true;
    for (int i = 0; i < n; i++) {
        success = breadth_first_search(graph, i, parts, visited);
        if (!success) break;
    }
    success ? cout << "YES" : cout << "NO";

    return 0;
}