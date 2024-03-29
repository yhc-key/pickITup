"use client";
import { useState, useEffect, useCallback, useRef } from "react";
import { useMediaQuery } from "react-responsive";
import Image from "next/image";
import { useRouter } from "next/navigation";

import { OXQuizDataMap } from "@/data/OXQuizData";
import BackBtn from "@/components/game/backBtn";
import Question from "@/components/game/OXQuiz/question";
import TrueBtn from "@/components/game/OXQuiz/trueBtn";
import FalseBtn from "@/components/game/OXQuiz/falseBtn";
import QuizResult from "@/components/game/OXQuiz/quizReulst";

interface Quiz {
  question: string;
  answer: boolean;
}

interface Answer {
  question: string;
  answer: boolean;
  user: boolean;
  correct: boolean;
  index: number;
}

export default function OXQuiz(props: any) {
  const isMobile = useMediaQuery({
    query: "(max-width:480px)",
  });

  const router = useRouter();

  const userAnswerRef = useRef<boolean | null>(null);
  const [index, setIndex] = useState(0);
  const [questionList, setQuestionList] = useState<Quiz[]>([]);
  const [answer, setAnswer] = useState<Answer[]>([]);

  // 선택한 주제
  const pickTech: string = props.params.pickTech;

  const apiUrl = "https://spring.pickitup.online/quizzes/ox";

  useEffect(() => {
    const fetchOXQuizData = async () => {
      try {
        // api로부터 데이터 받아오기
        const resp: Response = await fetch(`${apiUrl}/${pickTech}`);
        // HTTP 응답을 JSON객체로 변환
        const data: any = await resp.json();

        setQuestionList(data.response);
      } catch (error) {
        console.error(error);
      }
    };
    fetchOXQuizData();
  }, [apiUrl, pickTech, setQuestionList]);

  // useEffect(() => {
  //   // 선택한 주제에 대한 질문 받아오기
  //   const questions: Quiz[] | undefined = OXQuizDataMap.get(pickTech);
  //   if (questions) {
  //     setQuestionList(questions);
  //   }
  // }, [pickTech]);

  // trueBtn 클릭 시 실행되는 함수
  const TrueClickHandler = (): void => {
    userAnswerRef.current = true; // 사용자 답변을 true로 설정
    onNextClick();
  };

  // falseBtn 클릭 시 실행되는 함수
  const FalseClickHandler = (): void => {
    userAnswerRef.current = false; // 사용자 답변을 false로 설정
    onNextClick();
  };

  const addValueToAnswer = useCallback(() => {
    if (index >= 0) {
      // userAnswerRef를 통해 사용자의 답변 가져오기
      const curAnswer: boolean =
        userAnswerRef.current !== null ? userAnswerRef.current : false;

      // answer 배열에 추가
      setAnswer([
        ...answer,
        // 질문, 사용자 입력값, 정답유무, 문제번호
        {
          question: questionList[index].question,
          answer: questionList[index].answer,
          user: curAnswer,
          correct: curAnswer === questionList[index].answer, // 정답 여부 확인
          index: index + 1,
        },
      ]);
    }
  }, [questionList, answer, index]);

  // 다음문제로 넘어가기
  const onNextClick: () => void = useCallback(() => {
    addValueToAnswer();
    // 문제번호 1 증가
    setIndex((prev: number) => prev + 1);
  }, [addValueToAnswer]);

  const listCilckHandler = (): void => {
    router.push("/main/game");
  };

  return (
    <div className="flex flex-col">
      {/* <div>{props.params.pickTech}</div> */}
      {questionList && questionList[index] ? (
        <div>
          <div className="mx-10 mt-4 mb:mx-5 mb:mt-5">
            <BackBtn />
          </div>
          <div className="flex flex-wrap items-center justify-center mb:mt-12">
            <div className="flex flex-col mx-1 ml-20">
              <div className="flex flex-wrap justify-center my-3 text-4xl font-semibold tracking-widest">
                <div className="mr-3 text-f5green-300">OX</div>
                <div className="text-f5black-400">퀴즈</div>
              </div>
            <div className="text-xs text-f5black-400">
              문제를 읽고 알맞은 정답을 선택해주세요!
            </div>
            </div>
            {isMobile ? (
              <Image
                src="/images/oxIntro.png"
                alt="oxQuizIntro"
                width={95}
                height={65}
                priority={true}
              />
            ) : (
              <Image
                src="/images/oxIntro.png"
                alt="oxQuizIntro"
                width={190}
                height={130}
                priority={true}
              />
            )}
       
          </div>
          <Question question={questionList[index]} index={index + 1} />
          <div className="flex flex-wrap justify-center mt-10">
            <TrueBtn onNextClick={TrueClickHandler} />
            <FalseBtn onNextClick={FalseClickHandler} />
          </div>
        </div>
      ) : (
        <div className="my-4 mb:mt-12">
          <QuizResult answer={answer} />
            <div className="flex justify-end mt-8 mr-28 mb:absolute mb:top-1 mb:right-1 mb:mr-6">
            <button
              onClick={listCilckHandler}
              className="px-5 py-2 text-sm font-semibold bg-opacity-80 rounded-2xl  text-f5black-400 bg-f5gray-300 hover:bg-f5gray-400 ring-1 ring-inset ring-f5gray-400/10"
            >
              {"게임 목록 >>"}
            </button>
          </div>
        </div>
      )}
    </div>
  );
}