"use client";

export default function AddEssay() {
  const submitHandler = () => {};
  return (
    <form
      onSubmit={submitHandler}
      className="border border-f5gray-500 rounded-2xl h-full py-6 pl-16 flex flex-col"
    >
      <div className="flex flex-row items-center ">
        <span>지원한 회사 :</span>
        <input placeholder="회사명을 입력해주세요" className="ml-4" />
      </div>
    </form>
  );
}
