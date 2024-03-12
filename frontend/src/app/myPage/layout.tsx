"use client";




export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
      <div className="flex flex-row">
        <div>
         조싸피
        </div>
        <div className="flex-grow">{children}</div>
      </div>
  );
}
