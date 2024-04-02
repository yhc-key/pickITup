"use client";

import useEssayStore, { EssayState } from "@/store/essayStore";
import { useRouter } from "next/navigation";
import { Fragment, useRef, useState, FormEvent, useEffect } from "react";
import { FaMinus, FaPlus } from "react-icons/fa";
import { useMediaQuery } from "react-responsive";

interface Title {
  id: number;
  title: string;
}
interface Essay {
  title: string;
  content: string;
  company: string;
}

const apiAddress = "https://spring.pickITup.online/self/main";

export default function AddEssay() {
  const companyRef = useRef<HTMLInputElement | null>();
  const [companyIsEmpty, setCompanyIsEmpty] = useState<boolean>(false);
  const textAreaTitleRefs = useRef<(HTMLTextAreaElement | null)[]>([]);
  const textAreaContentRefs = useRef<(HTMLTextAreaElement | null)[]>([]);
  const router = useRouter();
  const essayTitles: Title[] = useEssayStore(
    (state: EssayState) => state.essayTitles
  );

  const [titleActive, setTitleActive] = useState<boolean[]>([]);
  const [isEmpty, setIsEmpty] = useState<boolean[]>([]);
  const [accessToken, setAccessToken] = useState<string | null>(null);

  const isMobile = useMediaQuery({
    query: "(max-width:480px)",
  });

  const submitHandler = async (event: FormEvent<HTMLFormElement>) => {
    event?.preventDefault();

    if (
      companyRef.current?.value === "" ||
      companyRef.current?.value === null
    ) {
      setCompanyIsEmpty(true);
      return;
    }
    setCompanyIsEmpty(false);
    let canFetch = true;
    let tmpIsEmptyArr: boolean[] = [];
    for (let i = 0; i < essayTitles.length; i++) {
      if (!titleActive[i]) {
        continue;
      }
      if (
        textAreaTitleRefs.current[i]?.value === "" ||
        textAreaContentRefs.current[i]?.value === ""
      ) {
        tmpIsEmptyArr[i] = true;
        canFetch = false;
      } else {
        tmpIsEmptyArr[i] = false;
      }
    }
    if (canFetch) {
      let urls: string[] = [];
      let sendEssays: Essay[] = [];
      for (let i = 0; i < essayTitles.length; i++) {
        if (!titleActive[i]) {
          continue;
        }
        urls.push(`${apiAddress}/${essayTitles[i].id}/sub`);
        sendEssays.push({
          title: textAreaTitleRefs.current[i]!.value,
          content: textAreaContentRefs.current[i]!.value,
          company: companyRef.current!.value,
        });
      }
      await Promise.all(
        urls.map(
          (url: string, index: number): Promise<Response> =>
            fetch(url, {
              method: "POST",
              headers: {
                "Content-Type": "application/json",
                Authorization: "Bearer " + accessToken,
              },
              body: JSON.stringify(sendEssays[index]),
            })
        )
      );
      router.push("/main/myPage/myEssay");
    } else {
      setIsEmpty(tmpIsEmptyArr);
    }
  };

  const addClickHandler = (index: number) => {
    const arr: boolean[] = [...titleActive];
    arr[index] = !arr[index];
    setTitleActive(arr);
  };

  const setCompanyRef = (ref: HTMLInputElement | null) => {
    companyRef.current = ref;
  };

  const setTextAreaTitleRef =
    (index: number) => (ref: HTMLTextAreaElement | null) => {
      textAreaTitleRefs.current[index] = ref;
    };
  const setTextAreaContentRef =
    (index: number) => (ref: HTMLTextAreaElement | null) => {
      textAreaContentRefs.current[index] = ref;
    };

  useEffect(() => {
    setAccessToken(sessionStorage.getItem("accessToken"));
  }, []);

  return (
    <Fragment>
      {essayTitles.length === 0 && (
        <div className="flex justify-center items-center text-lg text-f5green-500 h-full">
          먼저 자소서 항목을 추가해주세요
        </div>
      )}
      {essayTitles.length !== 0 && (
        <form
          onSubmit={submitHandler}
          className={`border border-f5gray-500 rounded-2xl h-full pt-6 pb-20 px-16 flex flex-col relative ${isMobile ? "mx-1 px-2" : ""}`}
        >
          <div className="flex flex-row items-center ml-4">
            <span>지원한 회사 :</span>
            <input
              ref={setCompanyRef}
              placeholder="회사명을 입력해주세요"
              className="ml-4 focus:bg-white focus:outline-f5green-300 justify-center"
            />
            <div className={`text-f5red-300 ${companyIsEmpty ? "" : "hidden"}`}>
              지원환 회사이름을 적어주세요
            </div>
          </div>
          {essayTitles.map((title: Title, index: number) => (
            <Fragment key={title.id}>
              <div
                className={`w-full flex flex-row border border-black rounded-lg p-2 min-h-10 justify-between ${index === 0 ? "mt-12" : "mt-2"}`}
              >
                <span className="w-full mr-10 outline-none">{`${index + 1}. ${title.title}`}</span>
                <div className="flex flex-row gap-6 mr-4 text-lg">
                  <button type="button" onClick={() => addClickHandler(index)}>
                    {!titleActive[index] ? (
                      <FaPlus className="text-f5green-300" />
                    ) : (
                      <FaMinus className="text-f5green-300" />
                    )}
                  </button>
                </div>
              </div>
              {titleActive?.[index] && (
                <Fragment>
                  <textarea
                    ref={setTextAreaTitleRef(index)}
                    placeholder="회사별 자세한 항목을 써주세요"
                    className="w-full p-2 mt-2 text-sm border border-black rounded-t-lg border-b-white min-h-16"
                  />
                  <textarea
                    ref={setTextAreaContentRef(index)}
                    placeholder="자기소개서 내용을 써주세요"
                    className="w-full p-2 text-sm border border-black rounded-b-lg min-h-40"
                  />
                </Fragment>
              )}
              <div
                className={`text-f5red-300 ${titleActive[index] && isEmpty[index] ? "" : "hidden"}`}
              >
                빈 항목 없도록 해주세요
              </div>
            </Fragment>
          ))}
          <div
            className={`absolute bottom-0 right-0 ${isMobile ? "mr-2 mb-4" : "mr-6 mb-6"}`}
          >
            <button
              type="button"
              onClick={() => router.push("/main/myPage/myEssay")}
              className={`px-6 py-2 text-white rounded-lg bg-f5red-300 ${isMobile ? "mr-4" : "mr-6"}`}
            >
              취소하기
            </button>
            <button
              type="submit"
              className={`px-6 py-2 text-white rounded-lg bg-f5green-300 ${isMobile ? "" : "mr-10"}`}
            >
              등록하기
            </button>
          </div>
        </form>
      )}
    </Fragment>
  );
}
