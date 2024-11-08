from flask import Flask, request
from dotenv import load_dotenv
import os
from openai import OpenAI
from sqlalchemy import text
import time

load_dotenv()

app = Flask(__name__)

api_key = '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'
client = OpenAI(api_key=api_key)

messages = [] # 메세지들이 담기는 공간 => 챗봇 (채팅 내역 6개월동안 보관 법적으로 필요) / 유럽진출(유로6)


def wait_on_run(run, thread):
    # 주어진 실행(run)이 완료될 때까지 대기합니다.
    # status 가 "queued" 또는 "in_progress" 인 경우에는 계속 polling 하며 대기합니다.
    while run.status == "queued" or run.status == "in_progress":
        # run.status 를 업데이트합니다.
        run = client.beta.threads.runs.retrieve(
            thread_id=thread.id,
            run_id=run.id,
        )
        # API 요청 사이에 잠깐의 대기 시간을 두어 서버 부하를 줄입니다.
        time.sleep(0.5)
    return run


def make_prompt(user_input):
    #생성한 gpts의 id 설정.(open ai의 어떤 assistats사용할 건지)
    assistant =client.beta.assistants.retrieve('asst_dH7HMmoi4cQiUxRACIT6MFBK')
    thread=client.beta.threads.create()
    message=client.beta.threads.messages.create(thread_id=thread.id
                                        ,role="user"
                                        , content=user_input)
    
    run=client.beta.threads.runs.create(
        thread_id=thread.id,
        assistant_id=assistant.id
        #instruction = ""
    )

    #응답 기다림.
    run = wait_on_run(run=run, thread=thread)

    message=client.beta.threads.messages.list(thread.id)

    #응답의 최상위 가져옴. -> 가장 최신이 맨 위.
    res = message.data[0].content[0].text.value

    
    return res

@app.route('/', methods=["GET", "POST"])
def index():
    # db = next(get_db())
    data = request.get_data()
    input=data.decode('utf-8')

    print(input)
    


    if request.method == 'POST':
        response = make_prompt(user_input=input)
        print("chatBot : \n",response)
        messages = {'bot' : response}
        
    
    return messages


if __name__ == "__main__":
    app.run(debug=True)