class LFUCache {
    struct Node {
        int key, val, freq;
        Node *prev, *next;
        Node(int k, int v) {
            key = k;
            val = v;
            freq = 1;
            prev = next = nullptr;
        }
    };

    struct List {
        Node *head, *tail;
        int sz;
        List() {
            head = new Node(0, 0);
            tail = new Node(0, 0);
            head->next = tail;
            tail->prev = head;
            sz = 0;
        }

        void addFront(Node *node) {
            node->next = head->next;
            node->prev = head;
            head->next->prev = node;
            head->next = node;
            sz++;
        }

        void removeNode(Node *node) {
            node->prev->next = node->next;
            node->next->prev = node->prev;
            sz--;
        }

        Node* removeLast() {
            if (sz == 0) return nullptr;
            Node *node = tail->prev;
            removeNode(node);
            return node;
        }
    };

    int capacity, minFreq;
    unordered_map<int, Node*> keyMap;
    unordered_map<int, List*> freqMap;

    void update(Node *node) {
        int f = node->freq;
        freqMap[f]->removeNode(node);

        if (f==minFreq && freqMap[f]->sz == 0)
            minFreq++;
        
        node->freq++;

        if (!freqMap.count(node->freq))
            freqMap[node->freq] = new List();

        freqMap[node->freq]->addFront(node);
    }
public:
    LFUCache(int capacity) {
        this->capacity = capacity;
        minFreq = 0;
    }
    
    int get(int key) {
        if (!keyMap.count(key))
            return -1;

        Node *node = keyMap[key];
        update(node);
        return node->val;
    }
    
    void put(int key, int value) {
        if (capacity == 0)
        return;

        if (keyMap.count(key)) {
            Node *node = keyMap[key];
            node->val = value;
            update(node);
            return;
        }

        if ((int)keyMap.size() == capacity) {
            Node *node = freqMap[minFreq]->removeLast();
            keyMap.erase(node->key);
            delete node;
        }

        Node *node = new Node(key, value);
        minFreq = 1;

        if (!freqMap.count(1))
            freqMap[1] = new List();

        freqMap[1]->addFront(node);
        keyMap[key] = node;
    }
};

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache* obj = new LFUCache(capacity);
 * int param_1 = obj->get(key);
 * obj->put(key,value);
 */