"use client";
import { useState, useEffect, useCallback } from "react";
import { useMediaQuery } from "react-responsive";
import Image from "next/image";
import Link from "next/link";
import { useRouter } from "next/navigation";

import { interviewDataMap } from "@/data/interviewData";
import Question from "@/components/interview/question";
import TimeBar from "@/components/interview/timebar";
import NextBtn from "@/components/interview/nextBtn";
import BackBtn from "@/components/interview/backBtn";
import useAuthStore, { AuthState } from "@/store/authStore";
import GameLoading from "@/components/gameLoading";
import CheckExpire from "@/data/checkExpire";

interface Quiz {
  id: number;
  question: string;
}

interface Answer {
  question: string;
  user: string;
  index: number;
}

export default function InterView(props: any) {
  const isMobile = useMediaQuery({
    query: "(max-width:480px)",
  });

  const router = useRouter();

  const [index, setIndex] = useState(0);
  const [questionList, setQuestionList] = useState<Quiz[]>([]);
  const [answer, setAnswer] = useState<Answer[]>([]);
  const [questionLength, setQuestrionLength] = useState(0);
  const [loading, setLoading] = useState(true);

  // 선택한 주제
  const pickTech: string = props.params.pickTech;

  const apiUrlGet = "https://spring.pickitup.online/interviews/random";
  const apiUrlPost = "https://spring.pickitup.online/my/interviews";
  const isLoggedIn: boolean = useAuthStore(
    (state: AuthState) => state.isLoggedIn
  );

  useEffect(() => {
    const fetchInterviewData = async () => {
      CheckExpire();
      const accessToken = sessionStorage.getItem("accessToken");
      try {
        setLoading(true);
        // api로부터 데이터 받아오기
        const resp: Response = await fetch(
          `${apiUrlGet}?subCategory=${pickTech}`,
          {
            headers: {
              Authorization: "Bearer " + accessToken,
            },
          }
        );
        // HTTP 응답을 JSON 객체로 변환
        const data: any = await resp.json();

        setQuestionList(data.response);
        setQuestrionLength(data.response.length);
        setLoading(false);
      } catch (error) {
        console.error(error);
        setLoading(false);
      }
    };
    fetchInterviewData();
  }, [apiUrlGet, pickTech, setQuestionList]);

  // 정답 정보 저장
  const addValueToAnswer = useCallback(
    async (questionId: number) => {
      if (index >= 0 && index < questionLength) {
        let curAnswer: string = "";
        document
          .querySelectorAll<HTMLTextAreaElement>(".question-textarea")
          .forEach((textArea) => {
            curAnswer += textArea.value.trim() + " ";
          });

        try {
          CheckExpire();
          const accessToken = sessionStorage.getItem("accessToken");
          await fetch(`${apiUrlPost}/${questionId}`, {
            method: "POST",
            headers: {
              "Content-Type": "text/plain",
              Authorization: "Bearer " + accessToken,
            },
            body: curAnswer.trim(), // 텍스트 직접 전달
          });
        } catch (error) {
          console.log(error);
        }
      }
    },
    [index, questionLength]
  );

  // 다음문제로 넘어가기
  const onNextClick: (questionId: number) => void = useCallback(
    (questionId: number) => {
      addValueToAnswer(questionId);
      // 문제번호 1 증가
      setIndex((prev: number) => prev + 1);
      // textarea 초기화
      document
        .querySelectorAll<HTMLTextAreaElement>(".question-textarea")
        .forEach((textArea) => {
          textArea.value = "";
        });
    },
    [addValueToAnswer]
  );

  return (
    <div className="flex flex-col">
      {loading ? (
        <div></div>
      ) : (questionList && questionList.length > 0 ? (
        questionList[index] ? (
          <div>
            <div className="mx-10 mt-3 mb:mx-5 mb:mt-5">
              <BackBtn />
            </div>
            <div className="flex flex-wrap justify-center mx-auto mb-1">
              <div className="flex flex-col justify-evenly">
                <div className="flex flex-wrap items-center justify-center text-5xl font-semibold tracking-wider mb:text-3xl">
                  {isMobile ? (
                    <Image
                      src="/images/pencil.png"
                      alt="pencil"
                      width={80}
                      height={80}
                      priority={true}
                    />
                  ) : (
                    <Image
                      src="/images/pencil.png"
                      alt="pencil"
                      width={120}
                      height={120}
                      priority={true}
                    />
                  )}

                  <div className="my-2 mr-1 text-f5green-300">면접 대비</div>
                  <div className="my-2 ml-2 text-f5black-400">QUIZ</div>
                </div>
              </div>
            </div>
            <Question
              question={questionList[index]}
              index={index + 1}
              onNextClick={() => onNextClick(questionList[index].id)}
            />
            <TimeBar
              onNextClick={() => onNextClick(questionList[index].id)}
              index={index}
            />
            <NextBtn onNextClick={() => onNextClick(questionList[index].id)} />
          </div>
        ) : (
          <div className="flex mb:flex-col min-h-[450px] mx-auto w-full mb:w-[350px] items-center justify-center">
            <div className="flex justify-cenetr mr-20 mb:mr-0">
              <Image
                src="/images/ghost.png"
                alt="ghost"
                width={240}
                height={240}
                className=" animate-[bounce_2s_ease-in-out_infinite] mt-28"
              ></Image>
            </div>
            <div className="flex flex-col justify-start items-start mb:justify-center mb:items-center">
              <div className="mb:text-2xl text-3xl font-semibold mb-10">
                <b className="text-f5green-300">준비된 퀴즈가 끝났습니다!</b>
              </div>
              <div className="text-lg mb:text-base"><Link href="/main/myPage/myPastAns" className="animate-[pulse_2s_ease-in_infinite] hover:font-semibold">마이페이지</Link>에서 예시답변을 확인해주세요.</div>
            </div>
          </div>
        )
      ) : (
        <GameLoading />
      )
      )}
    </div>
  );
}
