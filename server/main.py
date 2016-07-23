from flask import Flask, redirect
import json

from topic_tree import TopicTree

app = Flask(__name__)
topic_tree = TopicTree()

@app.route('/subjects')
def subjects():
    subjects = topic_tree.nodes_at_level(1)
    return json.dumps({
        'topics': subjects
    })

@app.route('/topics')
def topics():
    topics = topic_tree.nodes_at_level(2)
    return json.dumps({
        'topics': topics
    })

@app.route('/tutorials')
def tutorials():
    tutorials = topic_tree.nodes_at_level(3)
    return json.dumps({
        'topics': tutorials
    })


@app.route('/topic/<string:slug>')
def children(slug):
    children = topic_tree.children_of_topic_with_slug(slug)
    return json.dumps({
        'topics': children
    })

@app.after_request
def add_header(response):
    response.cache_control.max_age = 300
    return response

if __name__ == '__main__':
    app.run()
