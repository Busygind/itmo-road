//
// Created by Dmitryb on 01.05.2023.
//

#include <iostream>
#include <list>

using namespace std;

void depth_first_search(int bank, int *destroyed, pair<int, list<int>> *graph) {
    graph[bank].first = 1;
    for (int next: graph[bank].second) {
        if (graph[next].first == 1) (*destroyed)++;
        else if (graph[next].first == 0) depth_first_search(next, destroyed, graph);
    }
    graph[bank].first = 2;
}

int main() {
    int n, key;
    cin >> n;
    int destroyed = 0;
    pair<int, list<int>> graph[n];
    for (int i = 0; i < n; i++) {
        cin >> key;
        graph[--key].second.push_back(i);
    }

    for (int i = 0; i < n; i++)
        if (graph[i].first == 0) depth_first_search(i, &destroyed, graph);

    cout << destroyed;

    return 0;
}