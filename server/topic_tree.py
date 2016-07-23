import json

_DOMAIN_WHITELIST = ['math', 'science', 'economics-finance-domain']

class TopicTree(object):
    def __init__(self):
        with open('topictree.json', 'r') as f:
            self._json = json.load(f)

            # Prep all the fields.
            self._children_by_slug = {}

            def add_children(node):
                if node['kind'] == 'Topic':
                    slug = node['slug']
                    self._children_by_slug[slug] = []
                    for child_node in node['children']:
                        fields = self._extract_fields(child_node, node)
                        if fields:
                            self._children_by_slug[slug].append(fields)
                            add_children(child_node)

            add_children(self._json['rootNode'])

    def _extract_fields(self, node, parent):
        if node['kind'] == 'Topic':
            return {
                'kind': node['kind'],
                'slug': node['slug'],
                'title': node['title'],
                'domainSlug': node['domainSlug']
            }
        elif node['kind'] == 'Video':
            return {
                'kind': node['kind'],
                'youtubeId': node['youtubeId'],
                'title': node['title'],
                'domainSlug': parent['domainSlug'],
                'readableId': node['readableId']
            }
        else:
            return None

    def nodes_at_level(self, level):
        return self._nodes_at_level(level, self._json['rootNode']['children'])

    def _nodes_at_level(self, level, nodes_at_level):
        if level > 0:
            nodes = []
            for node in nodes_at_level:
                if node['kind'] == 'Topic':
                    nodes.extend(self._nodes_at_level(level - 1, node['children']))
            return nodes
        else:
            nodes = []
            for node in nodes_at_level:
                if node['kind'] == 'Topic':
                    nodes.append(self._extract_fields(node, {}))
            return nodes

    def children_of_topic_with_slug(self, slug):
        return self._children_by_slug[slug]
