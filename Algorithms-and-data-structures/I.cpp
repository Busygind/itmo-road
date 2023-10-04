//
// Created by Dmitryb on 05.04.2023.
//

#include <iostream>
#include <vector>
#include <set>
#include <list>
#define MAX_VAL 500001

using namespace std;

typedef struct {
    int index;
    int next;
} car_as_node;

bool custom_comparator (const car_as_node& a, const car_as_node& b) {
    return a.next > b.next;
}

vector<int> cars;
vector<list<int>> cars_indexes;
set<car_as_node, decltype(custom_comparator)*> cars_on_floor(custom_comparator);


int main() {
    int n, k, p, actions = 0;
    cin >> n >> k >> p;

    cars_indexes.resize(n + 1);
    cars.reserve(p);

    for (int i = 0; i < p; i++) {
        int car;
        cin >> car;
        cars_indexes[car].push_back(i);
        cars.push_back(car);
    }

    for (int i: cars) {
        car_as_node e = {i, cars_indexes[i].front()};
        cars_indexes[i].pop_front();

        int ix = cars_indexes[i].empty() ? MAX_VAL : cars_indexes[i].front();

        auto car = cars_on_floor.end();
        if ((car = cars_on_floor.find(e)) != cars_on_floor.end()) {
            auto new_car = *car;
            new_car.next = ix;
            cars_on_floor.erase(car);
            cars_on_floor.insert(new_car);
            continue;
        }
        actions++;
        if (cars_on_floor.size() == k) cars_on_floor.erase(cars_on_floor.begin());
        cars_on_floor.insert({.index = i, .next = ix});
    }
    cout << actions;
}