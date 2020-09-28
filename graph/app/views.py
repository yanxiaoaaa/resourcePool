from app import app
from flask import render_template,jsonify,request
from py2neo import Graph
import json

graph  = Graph(scheme="http://localhost:7474/browser/",username='neo4j',password='yanxiao')


@app.route('/')
@app.route('/index', methods = ['post','get'])
def index():
	data_send={}
	f=open('F:/yanxiao/毕业设计/demo/query.txt', 'r', encoding='utf-8')
	line=str(f.readline())
	res=line.split(' ')
	name=res[0]
	types=res[1]
	# name = request.values['name']
	# types = request.values['type']
	print(name+" "+types)
	f.close()
	node0=[]
	node1=[]
	relation=[]
	results=graph.run("MATCH (n:"+types+"{name:'"+name+"'})-[r:工作]->(p) RETURN n,p,r LIMIT 25")
	for result in results.data():
		n=result['n']
		p=result['p']
		r=result['r']
		names=n.get('name')
		contains=True
		for node in node0 :
			if node['name']==names:
				contains=False
				break
		if contains:
			node0_={}
			node0_['name']=n.get('name')
			st=str(n)
			num=0
			re=""
			while num<len(st):
				c=st[num]
				while c!=':':
					num=num+1
					c=st[num]
				num=num+1
				c=st[num]
				while c!=' ':
					re=re+c
					num=num+1
					c=st[num]
				break
			print(re)
			node0_['type']=re
			node0.append(node0_)
		contains=True
		names=p.get('name')
		for node in node1 :
			if node['name']==names:
				contains=False
				break
		if contains:
			node1_={}
			node1_['name']=p.get('name')
			st=str(p)
			num=0
			re=""
			while num<len(st):
				c=st[num]
				while c!=':':
					num=num+1
					c=st[num]
				num=num+1
				c=st[num]
				while c!=' ':
					re=re+c
					num=num+1
					c=st[num]
				break
			node1_['type']=re
			node1.append(node1_)
		relation_={}
		relation_['source']=n.get('name')
		relation_['target']=p.get('name')
		relation_['relation']=r.get('name')
		relation.append(relation_)


	results=graph.run("MATCH (n)-[r:工作]->(p:"+types+"{name:'"+name+"'}) RETURN n,p,r LIMIT 25")
	for result in results.data():
		n=result['n']
		p=result['p']
		r=result['r']
		names=n.get('name')
		contains=True
		for node in node0 :
			if node['name']==names:
				contains=False
				break
		if contains:
			node0_={}
			node0_['name']=n.get('name')
			st=str(n)
			num=0
			re=""
			while num<len(st):
				c=st[num]
				while c!=':':
					num=num+1
					c=st[num]
				num=num+1
				c=st[num]
				while c!=' ':
					re=re+c
					num=num+1
					c=st[num]
				break
			node0_['type']=re
			node0.append(node0_)
		contains=True
		names=p.get('name')
		for node in node1 :
			if node['name']==names:
				contains=False
				break
		if contains:
			node1_={}
			node1_['name']=p.get('name')
			st=str(p)
			num=0
			re=""
			while num<len(st):
				c=st[num]
				while c!=':':
					num=num+1
					c=st[num]
				num=num+1
				c=st[num]
				while c!=' ':
					re=re+c
					num=num+1
					c=st[num]
				break
			node1_['type']=re
			node1.append(node1_)
		relation_={}
		relation_['source']=n.get('name')
		relation_['target']=p.get('name')
		relation_['relation']=r.get('name')
		relation.append(relation_)


	data_send['node0']=node0
	data_send['node1']=node1
	data_send['relation']=relation
	print(str(data_send))
	return json.dumps(data_send, ensure_ascii=False)