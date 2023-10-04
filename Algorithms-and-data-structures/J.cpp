//
// Created by Dmitryb on 05.04.2023.
//

#include <iostream>
#include <algorithm>
#include <queue>

using namespace std;

deque<int> left_queue;
deque<int> right_queue;

void balance_queues() {
    while (right_queue.size() > left_queue.size()) {
        left_queue.push_back(right_queue.front());
        right_queue.pop_front();
    }
    while (right_queue.size() + 1 < left_queue.size()) {
        right_queue.push_front(left_queue.back());
        left_queue.pop_back();
    }
}

int main() {
    char sign;
    int n, i;
    cin >> n;
    while (n--) {
        cin >> sign;
        if (sign == '*') {
            cin >> i;
            left_queue.push_back(i);
        }
        if (sign == '+') {
            cin >> i;
            right_queue.push_back(i);
        }
        if (sign == '-') {
            cout << left_queue.front() << endl;
            left_queue.pop_front();
        }
        balance_queues();
    }
    return 0;
}