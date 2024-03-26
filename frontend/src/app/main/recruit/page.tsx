"use client";

import { techDataMap } from "@/data/techData";
import {
  useInfiniteQuery,
  useQuery,
  useQueryClient,
} from "@tanstack/react-query";
import Image from "next/image";
import { Fragment, useEffect, useState } from "react";

interface Recruit {
  career: [number, number];
  company: string;
  companyId: number;
  dueDate: [number, number, number];
  id: number;
  preferredRequirements: string[];
  qualificationRequirements: string[];
  source: string;
  thumbnailUrl: string;
  title: string;
  url: string;
}

const apiAddress = "https://spring.pickITup.online/recruit";
const techDataValues = Array.from(techDataMap.values());

const recruitClickHandler = (url: string) => {
  window.open(url, "_blank");
};

const Recruits = () => {
  const fetchRecruits = async ({ pageParam }) => {
    const res = await fetch(`${apiAddress}?page=${pageParam}&size=9&sort=null`);
    return res.json();
  };
  const {
    data,
    error,
    fetchNextPage,
    hasNextPage,
    isFetching,
    isFetchingNextPage,
    status,
  } = useInfiniteQuery({
    queryKey: ["recruits"],
    queryFn: ({ pageParam }) => fetchRecruits({ pageParam }),
    initialPageParam: 0,
    getNextPageParam: (lastPage: number, pages) => {
      return pages.length;
    },
  });
  console.log(data);
  console.log(data?.pages[0].response.content)
  return status === "pending" ? (
    <p>Loading...</p>
  ) : status === "error" ? (
    <p>Error: {error.message}</p>
  ) : (
    <>
      <div className="flex flex-wrap justify-center ">
        {data.pages.map((page, i: number) =>
          page.response.content.map((recruit: Recruit, recruitI: number) => {
            <button
              type="button"
              onClick={() => recruitClickHandler(recruit.url)}
              key={recruitI}
              className="w-[30%] max-w-72 h-[400px] m-4 rounded-xl overflow-hidden flex flex-col shadow "
            >
              <Image
                src={recruit.thumbnailUrl}
                alt="thumbnail"
                width="300"
                height="300"
                className="shadow-inner shadow-black object-cover h-[50%]"
              />
              <p className="m-1 text-sm text-f5gray-500 text-left">
                {recruit.company}
              </p>
              <p className="text-f5black-300 font-bold h-12 text-left px-2">
                {recruit.title}
              </p>
              <div className="gap-2 flex flex-wrap">
                {recruit.qualificationRequirements.map((tech, i) => {
                  let techTmp = tech.replace(/\s/g, "");
                  techTmp = techTmp.replace(/#/g, "Sharp");
                  if (
                    techDataValues.some((techDataValueArr) =>
                      techDataValueArr.includes(techTmp)
                    )
                  )
                    return (
                      <div key={i}>
                        <Image
                          src={`/images/techLogo/${techTmp}.png`}
                          alt={tech}
                          width="100"
                          height="100"
                          className="h-10 w-auto"
                        />
                      </div>
                    );
                })}
              </div>
            </button>;
          })
        )}
      </div>
      <div>
        <button
          onClick={() => fetchNextPage()}
          disabled={!hasNextPage || isFetchingNextPage}
        >
          {isFetchingNextPage
            ? "Loading more..."
            : hasNextPage
              ? "Load More"
              : "Nothing more to load"}
        </button>
      </div>
      <div>{isFetching && !isFetchingNextPage ? "Fetching..." : null}</div>
    </>
  );
};

export default function Recruit() {
  const [data, setData] = useState<Content[] | null>(null);

  // useEffect(() => {
  //   const fetchRecruits = async () => {
  //     try {
  //       const res = await fetch(`${apiAddress}?page=0&size=9&sort=null`);
  //       if (!res.ok) {
  //         throw new Error("잘못된 페치");
  //       }
  //       const jsonData = await res.json();
  //       console.log(jsonData.response);
  //       setData(jsonData.response.content);
  //     } catch (error) {
  //       console.error(error);
  //     }
  //   };
  //   fetchRecruits();
  // }, []);

  return <Recruits />;

  //   <div className="flex flex-wrap justify-center ">
  //     {data?.map((recruit: Content, i: number) => (
  //       <button
  //         type="button"
  //         onClick={() => recruitClickHandler(recruit.url)}
  //         key={i}
  //         className="w-[30%] max-w-72 h-[400px] m-4 rounded-xl overflow-hidden flex flex-col shadow "
  //       >
  //         <Image
  //           src={recruit.thumbnailUrl}
  //           alt="thumbnail"
  //           width="300"
  //           height="300"
  //           className="shadow-inner shadow-black object-cover h-[50%]"
  //         />
  //         <p className="m-1 text-sm text-f5gray-500 text-left">
  //           {recruit.company}
  //         </p>
  //         <p className="text-f5black-300 font-bold h-12 text-left px-2">
  //           {recruit.title}
  //         </p>
  //         <div className="gap-2 flex flex-wrap">
  //           {recruit.qualificationRequirements.map((tech, i) => {
  //             let techTmp = tech.replace(/\s/g, "");
  //             techTmp = techTmp.replace(/#/g, "Sharp");
  //             if (
  //               techDataValues.some((techDataValueArr) =>
  //                 techDataValueArr.includes(techTmp)
  //               )
  //             )
  //               return (
  //                 <div key={i}>
  //                   <Image
  //                     src={`/images/techLogo/${techTmp}.png`}
  //                     alt={tech}
  //                     width="100"
  //                     height="100"
  //                     className="h-10 w-auto"
  //                   />
  //                 </div>
  //               );
  //           })}
  //         </div>
  //       </button>
  //     ))}
  //   </div>
  // );
}
