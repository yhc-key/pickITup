"use client";
import { useEffect, useState } from "react";
import { useMediaQuery } from "react-responsive";
import { Interview } from "@/type/interface";

export default function MyPastAns() {
  const [myFavList, setMyFavList] = useState<Interview[]>([]);
  const [isPastAnsHovered, setIsPastAnsHovered] = useState<number | null>(null);
  const [isExAnsHovered, setIsExAnsHovered] = useState<number | null>(null);

  const apiUrl = "https://spring.pickitup.online/my/interviews";

  useEffect(() => {
    const fetchMyInterviewData = async () => {
      const accessToken = sessionStorage.getItem("accessToken");
      try {
        const resp: Response = await fetch(apiUrl, {
          headers: {
            Authorization: "Bearer " + accessToken,
          },
        });
        const data: any = await resp.json();

        setMyFavList(data.response);
        console.log(data.response);
      } catch (error) {
        console.log(error);
      }
    };
    fetchMyInterviewData();
  }, [apiUrl, setMyFavList]);

  const pastAnsClickHandler = () => {};
  const exAnsClickHandler = () => {};
  return (
    <div className="w-full pr-4 mt-4">
      <div className="w-[100%] rounded-2xl border flex justify-center">
        <table className="w-[96%]">
          <thead className="border-b">
            <tr className="text-center text-base h-16 m-2">
              <th className="pl-2 w-1/12">번호</th>
              <th className="pl-2 w-1/12 text-center">대분류</th>
              <th className="w-1/12 text-center">소분류</th>
              <th className="pl-2 w-5/12">문제</th>
              <th className="w-2/12 text-center">과거 제출 내역</th>
              <th className="w-2/12 text-center">예시 답변</th>
            </tr>
          </thead>
          <tbody>
            {myFavList &&
              myFavList.map((interview: Interview, index: number) => (
                <tr
                  key={index}
                  className="h-20 text-center text-sm transition-all ease-in duration-200"
                >
                  <td>{interview.interviewId}</td>
                  <td className="font-bold">{interview.mainCategory}</td>
                  <td className="font-bold">{interview.subCategory}</td>
                  <td>{interview.question}</td>
                  <td className="text-center">
                    <button
                      type="button"
                      onMouseEnter={() => setIsPastAnsHovered(index)}
                      onMouseLeave={() => setIsPastAnsHovered(null)}
                      className="rounded-lg py-2 px-4 bg-f5greenn-100 text-f5greenn-200 font-bold transition-all ease-in hover:scale-105 "
                    >
                      더보기
                    </button>
                    <div
                      className={`transition-opacity ease-in duration-200 ${isPastAnsHovered === index ? "opacity-100" : "opacity-0"}`}
                    >
                      {isPastAnsHovered === index ? (
                        <div className="relative">
                          <div className="absolute right-0 top-2 px-5 py-4 w-[30rem] bg-f5gray-300 text-f5black-400 rounded-md text-sm">
                          <div className="absolute w-4 h-4 right-20 -top-1 origin-center rotate-45 border-t-[1rem]  border-t-f5gray-300"></div>
                            {interview.answer}
                          </div>
                        </div>
                      ) : null}
                    </div>
                  </td>
                  <td className="text-center">
                    <button
                      type="button"
                      onMouseEnter={() => setIsExAnsHovered(index)}
                      onMouseLeave={() => setIsExAnsHovered(null)}
                      className="rounded-lg py-2 px-4 bg-f5redd-100 text-f5redd-200 font-bold transition-all ease-in hover:scale-105 "
                    >
                      예시 답변
                    </button>
                    <div
                      className={`transition-opacity ease-in duration-200 ${isExAnsHovered === index ? "opacity-100" : "opacity-0"}`}
                    >
                      {isExAnsHovered === index ? (
                        <div className="relative">
                          <div className="absolute right-0 top-2 px-5 py-4 w-[30rem] bg-f5gray-300 text-f5black-400 rounded-md text-sm">
                          <div className="absolute w-4 h-4 right-20 -top-1 origin-center rotate-45 border-t-[1rem]  border-t-f5gray-300"></div>
                            {interview.example}
                          </div>
                        </div>
                      ) : null}
                    </div>
                  </td>
                </tr>
              ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}
